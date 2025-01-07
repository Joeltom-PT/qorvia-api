package com.qorvia.accountservice.service.admin;

import com.qorvia.accountservice.client.NotificationClient;
import com.qorvia.accountservice.dto.admin.request.ChangeOrganizerStatusRequest;
import com.qorvia.accountservice.dto.admin.request.ChangePasswordRequest;
import com.qorvia.accountservice.dto.admin.request.OrganizerStatusChangeMailRequest;
import com.qorvia.accountservice.dto.admin.response.GetAllOrganizersResponse;
import com.qorvia.accountservice.dto.admin.response.GetAllUsersResponse;
import com.qorvia.accountservice.dto.admin.response.OrganizerDetailDTO;
import com.qorvia.accountservice.dto.organizer.OrganizerDTO;
import com.qorvia.accountservice.dto.response.ApiResponse;
import com.qorvia.accountservice.dto.user.UserDTO;
import com.qorvia.accountservice.exception.ResourceNotFoundException;
import com.qorvia.accountservice.model.Roles;
import com.qorvia.accountservice.model.organizer.Organizer;
import com.qorvia.accountservice.model.organizer.OrganizerStatus;
import com.qorvia.accountservice.model.organizer.RegisterRequestStatus;
import com.qorvia.accountservice.model.user.UserInfo;
import com.qorvia.accountservice.model.user.UserStatus;
import com.qorvia.accountservice.repository.OrganizerRepository;
import com.qorvia.accountservice.repository.UserRepository;
import com.qorvia.accountservice.utils.ResponseUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService{

    private final UserRepository userRepository;
    private final OrganizerRepository organizerRepository;
    private final NotificationClient notificationClient;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public ResponseEntity<ApiResponse<GetAllUsersResponse>> getAllUsers(int page, int size, String search) {
        System.out.println("page " + page + " " + size);
        if (page < 0 || size <= 0) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), "Invalid page or size.", null));
        }

        try {
            Pageable pageable = PageRequest.of(page, size);

            Page<UserInfo> userPage;
            if (search != null && !search.trim().isEmpty()) {
                userPage = userRepository.findBySearch(search, pageable);
            } else {
                userPage = userRepository.findAll(pageable);
            }


            List<UserDTO> userDTOs = userPage.getContent().stream()
                    .map(this::convertToUserDTO)
                    .collect(Collectors.toList());

            GetAllUsersResponse usersResponse = new GetAllUsersResponse(
                    userDTOs,
                    userPage.getTotalElements(),
                    userPage.getTotalPages(),
                    page,
                    size
            );

            return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), "User list retrieved successfully.", usersResponse));

        } catch (DataAccessException e) {
            log.error("Database error fetching users: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Database error: " + e.getMessage(), null));
        } catch (Exception e) {
            log.error("Error fetching users: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "An error occurred while fetching users: " + e.getMessage(), null));
        }
    }


    public ResponseEntity<ApiResponse<GetAllOrganizersResponse>> getAllOrganizers(int page, int size, String search, String approvalStatus) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Organizer> organizerPage;

        RegisterRequestStatus statusFilter = null;
        if (StringUtils.hasText(approvalStatus)) {
            try {
                statusFilter = RegisterRequestStatus.valueOf(approvalStatus.toUpperCase());
            } catch (IllegalArgumentException e) {
                return ResponseUtil.buildResponse(HttpStatus.BAD_REQUEST, "Invalid approval status", null);
            }
        }

        if (!StringUtils.hasText(search)) {
            if (statusFilter == null) {
                organizerPage = organizerRepository.findAll(pageable);
            } else {
                organizerPage = organizerRepository.findAllByRegistrationStatus(statusFilter, pageable);
            }
        } else {
            if (statusFilter == null) {
                organizerPage = organizerRepository.findByOrganizationNameContainingIgnoreCase(search, pageable);
            } else {
                organizerPage = organizerRepository.findByOrganizationNameContainingIgnoreCaseAndRegistrationStatus(search, statusFilter, pageable);
            }
        }

        List<OrganizerDTO> organizerDTOList = organizerPage.stream()
                .filter(organizer -> organizer.getRoles() != Roles.ADMIN)
                .map(this::mapToOrganizerDTO)
                .collect(Collectors.toList());

        GetAllOrganizersResponse response = new GetAllOrganizersResponse();
        response.setOrganizers(organizerDTOList);
        response.setTotalElements(organizerPage.getTotalElements());
        response.setTotalPages(organizerPage.getTotalPages());
        response.setPageNumber(organizerPage.getNumber());
        response.setPageSize(organizerPage.getSize());

        return ResponseUtil.buildResponse(HttpStatus.OK, "Organizers fetched successfully", response);
    }

    @Override
    public ResponseEntity<ApiResponse<OrganizerDetailDTO>> getOrganizerDetails(Long id) {
        Optional<Organizer> organizerOpt = organizerRepository.findById(id);
        if (organizerOpt.isPresent()) {
            Organizer organizer = organizerOpt.get();
            OrganizerDetailDTO dto = OrganizerDetailDTO.builder()
                    .id(organizer.getId())
                    .organizationName(organizer.getOrganizationName())
                    .email(organizer.getEmail())
                    .phone(organizer.getPhone())
                    .website(organizer.getWebsite())
                    .address(organizer.getAddress())
                    .address2(organizer.getAddress2())
                    .city(organizer.getCity())
                    .country(organizer.getCountry())
                    .state(organizer.getState())
                    .facebook(organizer.getFacebook())
                    .instagram(organizer.getInstagram())
                    .twitter(organizer.getTwitter())
                    .linkedin(organizer.getLinkedin())
                    .youtube(organizer.getYoutube())
                    .profileImage(organizer.getProfileImage())
                    .about(organizer.getAbout())
                    .totalEvents(organizer.getTotalEvents())
                    .registrationStatus(organizer.getRegistrationStatus())
                    .status(organizer.getStatus())
                    .verificationStatus(organizer.getVerificationStatus())
                    .build();

            return ResponseUtil.buildResponse(HttpStatus.OK, "Organizer details retrieved successfully", dto);
        } else {
            return ResponseUtil.buildResponse(HttpStatus.NOT_FOUND, "Organizer not found", null);
        }
    }


    @Override
    public ResponseEntity<ApiResponse<String>> changeOrganizerStatus(Long id, ChangeOrganizerStatusRequest request) {
        Organizer organizer = organizerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Organizer not found"));

        StringBuilder emailMessage = new StringBuilder();

        boolean isChanged = false;

        if (request.getRegistrationStatus() != null) {
            if (!request.getRegistrationStatus().equals(organizer.getRegistrationStatus().name())) {
                switch (request.getRegistrationStatus()) {
                    case "APPROVED":
                        organizer.setRegistrationStatus(RegisterRequestStatus.APPROVED);
                        emailMessage.append("Your registration has been approved.");
                        isChanged = true;
                        break;
                    case "REJECTED":
                        organizer.setRegistrationStatus(RegisterRequestStatus.REJECTED);
                        emailMessage.append("Your registration has been rejected.");
                        isChanged = true;
                        break;
                    default:
                        return ResponseUtil.buildResponse(HttpStatus.BAD_REQUEST, "Invalid registration status", null);
                }
            }
        }

        if (request.getStatus() != null) {
            if (!request.getStatus().equals(organizer.getStatus().name())) {
                switch (request.getStatus()) {
                    case "ACTIVE":
                        organizer.setStatus(OrganizerStatus.ACTIVE);
                        emailMessage.append(" Your account is now active.");
                        isChanged = true;
                        break;
                    case "BLOCKED":
                        organizer.setStatus(OrganizerStatus.BLOCKED);
                        emailMessage.append(" Your account has been blocked.");
                        isChanged = true;
                        break;
                    default:
                        return ResponseUtil.buildResponse(HttpStatus.BAD_REQUEST, "Invalid organizer status", null);
                }
            }
        }

        if (!isChanged) {
            return ResponseUtil.buildResponse(HttpStatus.OK, "No changes made to the organizer status", null);
        }
        organizerRepository.save(organizer);

        OrganizerStatusChangeMailRequest mailRequest = new OrganizerStatusChangeMailRequest();
        mailRequest.setEmail(organizer.getEmail());
        mailRequest.setMessage(emailMessage.toString());

        if (request.getRegistrationStatus().equals("APPROVED")) {
            mailRequest.setRegisterRequestStatus(OrganizerStatus.valueOf(request.getStatus()).toString());
        } else {
            mailRequest.setRegisterRequestStatus(RegisterRequestStatus.REJECTED.toString());
        }

        try {
            notificationClient.organizerStatusChangeMail(mailRequest);
        } catch (Exception e) {
            log.error("Organizer status updated but failed to send email notification");
        }

        return ResponseUtil.buildResponse(HttpStatus.OK, "Organizer status updated successfully", null);
    }

    @Override
    public ResponseEntity<?> changeAdminPassword(ChangePasswordRequest changePasswordRequest, Long adminId) {
        Optional<Organizer> adminOptional = organizerRepository.findById(adminId);

        adminOptional.ifPresent(admin -> log.info("Admin password change request. Roles: {}", admin.getRoles()));

        if (adminOptional.isPresent()) {
            Organizer admin = adminOptional.get();

            if (admin.getRoles() != Roles.ADMIN) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body("Admin not found or the provided ID does not correspond to an ADMIN.");
            }

            if (changePasswordRequest.getCurrentPass() == null || changePasswordRequest.getCurrentPass().isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Current password cannot be null or empty.");
            }

            if (!passwordEncoder.matches(changePasswordRequest.getCurrentPass(), admin.getPassword())) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body("The old password does not match the current password.");
            }

            if (changePasswordRequest.getNewPass() == null || changePasswordRequest.getNewPass().isEmpty()) {
                return ResponseEntity.badRequest().body("New password cannot be empty.");
            }

            admin.setPassword(passwordEncoder.encode(changePasswordRequest.getNewPass()));
            organizerRepository.save(admin);

            return ResponseEntity.ok("Password successfully updated.");
        }

        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body("Admin not found or the provided ID does not correspond to an ADMIN.");
    }


    @Override
    public ResponseEntity<String> blockOrUnblockUser(String email) {
        try {
            Optional<UserInfo> optionalUser = userRepository.findByEmail(email);

            if (optionalUser.isPresent()) {
                UserInfo user = optionalUser.get();
                log.info("user block request in backend from admin with the email : {}", user.getEmail());

                if (user.getStatus().equals(UserStatus.ACTIVE)) {
                    user.setStatus(UserStatus.INACTIVE);
                    log.info("Logging out user with the email : {}", email);
                } else if (user.getStatus().equals(UserStatus.INACTIVE)) {
                    user.setStatus(UserStatus.ACTIVE);
                }

                userRepository.save(user);

                // Emit the updated status over WebSocket
//                socketIOServer.getRoomOperations("/user-status").sendEvent("userStatusUpdated", user);

                return ResponseEntity.ok("User status updated successfully.");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while updating user status.");
        }
    }



    private OrganizerDTO mapToOrganizerDTO(Organizer organizer) {
        return OrganizerDTO.builder()
                .id(organizer.getId())
                .name(organizer.getOrganizationName())
                .email(organizer.getEmail())
                .verificationStatus(organizer.getVerificationStatus())
                .registerRequestStatus(organizer.getRegistrationStatus())
                .status(organizer.getStatus())
                .role(organizer.getRoles())
                .build();
    }

    private UserDTO convertToUserDTO(UserInfo user) {
        return UserDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .role(user.getRoles())
                .verificationStatus(user.getVerificationStatus())
                .status(user.getStatus())
                .pro_img(user.getProfileImg())
                .build();
    }

}
