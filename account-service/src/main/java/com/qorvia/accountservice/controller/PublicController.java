package com.qorvia.accountservice.controller;

import com.qorvia.accountservice.service.jwt.JwtService;
import com.qorvia.accountservice.service.organizer.OrganizerService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/account/public")
@RequiredArgsConstructor
@Slf4j
public class PublicController {

    private final OrganizerService organizerService;
    private final JwtService jwtService;


    @GetMapping("/get-organizer-list")
    public ResponseEntity<?> getAllActiveOrganizersInfo(
            HttpServletRequest servletRequest,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "6") int size) {
        log.info("getting organizer list api called");
        Long userId = jwtService.getUserIdFormJwtToken(servletRequest);
        log.info("getting organizer list api called and user found with id : {}", userId);
        return ResponseEntity.ok(organizerService.getAllActiveOrganizersInfo(userId, page, size));
    }

    @GetMapping("/getOrganizerShortInfo/{id}")
    public ResponseEntity<?> getOrganizerShortInfo(@PathVariable("id") Long organizerId, HttpServletRequest servletRequest){
        log.info("Calling organizer short info api");
        Long userId = jwtService.getUserIdFormJwtToken(servletRequest);
        return ResponseEntity.ok(organizerService.getOrganizerShortInfo(organizerId,userId));
    }


    @GetMapping("/getOrganizerProfile/{id}")
    public ResponseEntity<?> getOrganizerProfileData(@PathVariable("id") int idStr, HttpServletRequest servletRequest) {
        log.info("Calling organizer profile data API with id: {}", idStr);
        try {
            Long organizerId = (long) idStr;
            Long userId = jwtService.getUserIdFormJwtToken(servletRequest);
            return ResponseEntity.ok(organizerService.getOrganizerProfileData(organizerId, userId));
        } catch (NumberFormatException e) {
            log.error("Invalid id: {}. It must be a numeric value.", idStr);
            return ResponseEntity.badRequest().body("Invalid organizer ID. It must be a numeric value.");
        }
    }



}
