package com.qorvia.accountservice.controller;

import com.qorvia.accountservice.dto.organizer.OrganizerLoginRequest;
import com.qorvia.accountservice.dto.organizer.OrganizerProfileDTO;
import com.qorvia.accountservice.dto.organizer.OrganizerRegisterRequest;
import com.qorvia.accountservice.dto.organizer.OrganizerSettingsDTO;
import com.qorvia.accountservice.dto.response.ApiResponse;
import com.qorvia.accountservice.service.jwt.JwtService;
import com.qorvia.accountservice.service.organizer.OrganizerService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/account/organizer")
@RequiredArgsConstructor
public class OrganizerController {

    private final OrganizerService organizerService;
    private final JwtService jwtService;

    @GetMapping("/getProfile")
    public ResponseEntity<?> getProfile(HttpServletRequest servletRequest){
        Long organizerId = jwtService.getUserIdFormJwtToken(servletRequest);
        return organizerService.getProfile(organizerId);
    }

    @PutMapping("/updateProfile")
    public ResponseEntity<?> updateProfile(HttpServletRequest servletRequest, @RequestBody OrganizerProfileDTO updatedProfile){
        Long organizerId = jwtService.getUserIdFormJwtToken(servletRequest);
        return organizerService.updateProfile(organizerId, updatedProfile);
    }

    @GetMapping("/settings")
    public ResponseEntity<?> getOrganizerSettings(HttpServletRequest httpServletRequest){
        Long organizerId = jwtService.getUserIdFormJwtToken(httpServletRequest);
        return organizerService.getOrganizerProfile(organizerId);
    }

    @PutMapping("/settings")
    public ResponseEntity<?> updateOrganizerSettings(@RequestBody OrganizerSettingsDTO organizerSettingsDTO, HttpServletRequest servletRequest){
        Long organizerId = jwtService.getUserIdFormJwtToken(servletRequest);
        return organizerService.updateOrganizerSettings(organizerSettingsDTO, organizerId);
    }

    @GetMapping("/get-organizers-for-collaboration")
    public ResponseEntity<?> getOrganizersForCollaborationRequest(
            @RequestParam(required = false, defaultValue = "10") int limit,
            @RequestParam(required = false) String search,
            HttpServletRequest servletRequest) {
        Long organizerId = jwtService.getUserIdFormJwtToken(servletRequest);
        return ResponseEntity.ok(organizerService.getCollaborationRequestApprovedOrganizers(limit, search, organizerId));
    }


    @GetMapping("/connect-account-for-payout")
    public ResponseEntity<?> getAccountConnectingLink(HttpServletRequest servletRequest) {
        try {
            Long organizerId = jwtService.getUserIdFormJwtToken(servletRequest);

            String accountLink = organizerService.createAccountOnboardingLink(organizerId);
            return ResponseEntity.ok(accountLink);

        } catch (Exception e) {
            return ResponseEntity.status(500).body("An error occurred: " + e.getMessage());
        }
    }



}
