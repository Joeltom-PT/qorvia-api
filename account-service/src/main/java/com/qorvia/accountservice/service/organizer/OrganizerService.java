package com.qorvia.accountservice.service.organizer;

import com.qorvia.accountservice.dto.admin.response.GetAllOrganizersResponse;
import com.qorvia.accountservice.dto.organizer.*;
import com.qorvia.accountservice.dto.organizer.response.ActiveOrganizersInfoResponse;
import com.qorvia.accountservice.dto.organizer.response.CollaborationRequestApprovedOrganizersDTO;
import com.qorvia.accountservice.dto.response.ApiResponse;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface OrganizerService {

    ResponseEntity<?> getProfile(Long organizerId);

    ResponseEntity<?> updateOrganizerSettings(OrganizerSettingsDTO organizerSettingsDTO, Long organizerId);

    ResponseEntity<?> getOrganizerProfile(Long organizerId);

    List<CollaborationRequestApprovedOrganizersDTO> getCollaborationRequestApprovedOrganizers(int limit, String search, Long organizerId);

    ActiveOrganizersInfoResponse getAllActiveOrganizersInfo(Long userId, int page, int size);

    ResponseEntity<?> updateProfile(Long organizerId, OrganizerProfileDTO updatedProfile);

    OrganizerShortInfo getOrganizerShortInfo(Long organizerId, Long userId);

    OrganizerProfileDTO getOrganizerProfileData(Long organizerId, Long userId);

    String createAccountOnboardingLink(Long organizerId);
}
