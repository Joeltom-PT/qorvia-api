package com.qorvia.accountservice.service.organizer;

import com.qorvia.accountservice.client.PaymentClient;
import com.qorvia.accountservice.dto.admin.response.GetAllOrganizersResponse;
import com.qorvia.accountservice.dto.organizer.*;
import com.qorvia.accountservice.dto.organizer.response.ActiveOrganizersInfoResponse;
import com.qorvia.accountservice.dto.organizer.response.CollaborationRequestApprovedOrganizersDTO;
import com.qorvia.accountservice.dto.request.StripeAccountOnboardingRequest;
import com.qorvia.accountservice.dto.response.ApiResponse;
import com.qorvia.accountservice.exception.ResourceNotFoundException;
import com.qorvia.accountservice.model.Follow;
import com.qorvia.accountservice.model.Roles;
import com.qorvia.accountservice.model.organizer.Organizer;
import com.qorvia.accountservice.model.organizer.OrganizerSettings;
import com.qorvia.accountservice.model.organizer.OrganizerStatus;
import com.qorvia.accountservice.model.organizer.RegisterRequestStatus;
import com.qorvia.accountservice.model.user.UserInfo;
import com.qorvia.accountservice.model.user.UserStatus;
import com.qorvia.accountservice.repository.FollowRepository;
import com.qorvia.accountservice.repository.OrganizerRepository;
import com.qorvia.accountservice.repository.OrganizerSettingsRepository;
import com.qorvia.accountservice.repository.UserRepository;
import com.qorvia.accountservice.utils.ResponseUtil;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrganizerServiceImpl implements OrganizerService {

    private final OrganizerRepository organizerRepository;
    private final UserRepository userRepository;
    private final OrganizerSettingsRepository organizerSettingsRepository;
    private final FollowRepository followRepository;
    private final PaymentClient paymentClient;

    @Override
    public ResponseEntity<?> getProfile(Long organizerId) {
        Optional<Organizer> organizer = organizerRepository.findById(organizerId);

        if (organizer.isPresent() && organizer.get().getRoles() == Roles.ORGANIZER) {
            OrganizerProfileDTO organizerProfileDTO = OrganizerProfileDTO.builder()
                    .organizationName(organizer.get().getOrganizationName())
                    .phone(organizer.get().getPhone())
                    .website(organizer.get().getWebsite())
                    .address(organizer.get().getAddress())
                    .address2(organizer.get().getAddress2())
                    .city(organizer.get().getCity())
                    .country(organizer.get().getCountry())
                    .state(organizer.get().getState())
                    .facebook(organizer.get().getFacebook())
                    .instagram(organizer.get().getInstagram())
                    .twitter(organizer.get().getTwitter())
                    .linkedin(organizer.get().getLinkedin())
                    .youtube(organizer.get().getYoutube())
                    .profileImage(organizer.get().getProfileImage())
                    .about(organizer.get().getAbout())
                    .build();

            return ResponseEntity.ok(organizerProfileDTO);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Organizer not found or invalid role");
    }

    @Override
    public ResponseEntity<?> updateProfile(Long organizerId, OrganizerProfileDTO updatedProfile) {
        Optional<Organizer> organizerOptional = organizerRepository.findById(organizerId);

        if (organizerOptional.isPresent() && organizerOptional.get().getRoles() == Roles.ORGANIZER) {
            Organizer organizer = organizerOptional.get();

            organizer.setOrganizationName(updatedProfile.getOrganizationName());
            organizer.setPhone(updatedProfile.getPhone());
            organizer.setWebsite(updatedProfile.getWebsite());
            organizer.setAddress(updatedProfile.getAddress());
            organizer.setAddress2(updatedProfile.getAddress2());
            organizer.setCity(updatedProfile.getCity());
            organizer.setCountry(updatedProfile.getCountry());
            organizer.setState(updatedProfile.getState());
            organizer.setFacebook(updatedProfile.getFacebook());
            organizer.setInstagram(updatedProfile.getInstagram());
            organizer.setTwitter(updatedProfile.getTwitter());
            organizer.setLinkedin(updatedProfile.getLinkedin());
            organizer.setYoutube(updatedProfile.getYoutube());
            organizer.setProfileImage(updatedProfile.getProfileImage());
            organizer.setAbout(updatedProfile.getAbout());

            organizerRepository.save(organizer);

            return ResponseEntity.ok("Profile update successful");
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Organizer not found or invalid role");
    }

    @Override
    public OrganizerShortInfo getOrganizerShortInfo(Long organizerId, Long userId) {
        try {
            Organizer organizer = organizerRepository.findById(organizerId)
                    .orElseThrow(() -> new EntityNotFoundException("Organizer not found"));

            boolean isFollowing = false;

            if (userId != null) {
                UserInfo user = userRepository.findById(userId)
                        .orElseThrow(() -> new EntityNotFoundException("User not found"));
                isFollowing = followRepository.existsByUserIdAndOrganizerId(userId, organizerId);
            }

            return OrganizerShortInfo.builder()
                    .id(organizer.getId())
                    .name(organizer.getOrganizationName())
                    .imgUrl(organizer.getProfileImage())
                    .isFollowing(isFollowing)
                    .totalEvents(organizer.getOrganizerStats().getTotalEventsCount())
                    .totalFollowers(organizer.getOrganizerStats().getFollowersCount())
                    .build();

        } catch (EntityNotFoundException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public OrganizerProfileDTO getOrganizerProfileData(Long organizerId, Long userId) {
        Organizer organizer = organizerRepository.findById(organizerId)
                .orElseThrow(() -> new IllegalArgumentException("Organizer not found"));

        boolean isFollowing = false;
        if (userId != null) {
            isFollowing = followRepository.existsByUserIdAndOrganizerId(userId, organizerId);
        }

        return OrganizerProfileDTO.builder()
                .id(organizer.getId())
                .organizationName(organizer.getOrganizationName())
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
                .isFollowing(isFollowing)
                .totalEvents(organizer.getOrganizerStats().getTotalEventsCount())
                .totalFollowers(organizer.getOrganizerStats().getFollowersCount())
                .build();
    }

    @Override
    public String createAccountOnboardingLink(Long organizerId) {
        Optional<Organizer> organizer = organizerRepository.findById(organizerId);
        if (organizer.isEmpty()) {
            throw new IllegalArgumentException("Organizer not found with ID: " + organizerId);
        }

        StripeAccountOnboardingRequest stripeAccountOnboardingRequest = StripeAccountOnboardingRequest.builder()
                .email(organizer.get().getEmail())
                .organizerId(organizerId)
                .build();

        ResponseEntity response = paymentClient.generateAccountLinkForOnboarding(stripeAccountOnboardingRequest);

        if (response.getStatusCode().is2xxSuccessful()) {
            String url = (String) response.getBody();

            if (url != null && !url.isEmpty()) {
                return url;
            } else {
                throw new RuntimeException("Failed to generate account onboarding link: URL is empty");
            }
        } else {
            throw new RuntimeException("Failed to generate account onboarding link: " + response.getStatusCode());
        }
    }


    public ResponseEntity<?> updateOrganizerSettings(OrganizerSettingsDTO organizerSettingsDTO, Long organizerId) {
        Organizer organizer = organizerRepository.findById(organizerId)
                .orElseThrow(() -> new ResourceNotFoundException("Organizer not found with id: " + organizerId));

        OrganizerSettings organizerSettings = organizerSettingsRepository.findByOrganizer(organizer)
                .orElseGet(() -> {
                    OrganizerSettings newSettings = new OrganizerSettings();
                    newSettings.setOrganizer(organizer);
                    return newSettings;
                });

        organizerSettings.setApprovalAllowed(organizerSettingsDTO.isApprovalAllowed());

        OrganizerSettings updatedSettings = organizerSettingsRepository.save(organizerSettings);

        return ResponseEntity.ok().body("Organizer settings updated");
    }

    @Override
    public ResponseEntity<?> getOrganizerProfile(Long organizerId) {
        Organizer organizer = organizerRepository.findById(organizerId).get();
        OrganizerSettings organizerSettings = organizerSettingsRepository.findByOrganizer(organizer).get();
        OrganizerSettingsDTO organizerSettingsDTO = new OrganizerSettingsDTO();
        organizerSettingsDTO.setApprovalAllowed(organizerSettings.isApprovalAllowed());
        return ResponseEntity.ok(organizerSettingsDTO);
    }

    @Override
    public List<CollaborationRequestApprovedOrganizersDTO> getCollaborationRequestApprovedOrganizers(int limit, String search, Long organizerId) {
        List<Organizer> organizers = organizerRepository.findApprovedOrganizersWithCriteria(search, limit, organizerId);
        return organizers.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ActiveOrganizersInfoResponse getAllActiveOrganizersInfo(Long userId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Organizer> activeOrganizersPage;

        activeOrganizersPage = organizerRepository.findAllByStatusAndRolesNot(OrganizerStatus.ACTIVE, Roles.ADMIN, pageable);

        List<OrganizerInfo> organizerInfoList = activeOrganizersPage.getContent().stream()
                .map(organizer -> {
                    boolean isFollowing = userId != null && followRepository.existsByUserIdAndOrganizerId(userId, organizer.getId());
                    return OrganizerInfo.builder()
                            .id(organizer.getId())
                            .name(organizer.getOrganizationName())
                            .description(organizer.getAbout())
                            .isFollowing(isFollowing)
                            .imageUrl(organizer.getProfileImage())
                            .stats(OrganizerInfo.OrganizerStatsDTO.builder()
                                    .followers(organizer.getOrganizerStats().getFollowersCount())
                                    .posts(organizer.getOrganizerStats().getTotalPostCount())
                                    .events(organizer.getOrganizerStats().getTotalEventsCount())
                                    .build())
                            .build();
                })
                .collect(Collectors.toList());

        organizerInfoList.forEach(o -> log.info("Taken organizer with id : {}, and name {}", o.getId(), o.getName()));

        return ActiveOrganizersInfoResponse.builder()
                .organizers(organizerInfoList)
                .totalElements(activeOrganizersPage.getTotalElements())
                .totalPages(activeOrganizersPage.getTotalPages())
                .pageNumber(activeOrganizersPage.getNumber())
                .pageSize(activeOrganizersPage.getSize())
                .build();
    }



    private CollaborationRequestApprovedOrganizersDTO convertToDTO(Organizer organizer) {
        CollaborationRequestApprovedOrganizersDTO dto = new CollaborationRequestApprovedOrganizersDTO();
        dto.setId(organizer.getId());
        dto.setName(organizer.getOrganizationName());
        log.info("Converting Organizer to DTO, ID: {}, Name: {}", dto.getId(), dto.getName());
        return dto;
    }


}
