package com.qorvia.accountservice.controller;

import com.qorvia.accountservice.dto.user.UserDTO;
import com.qorvia.accountservice.dto.user.request.PasswordResetRequest;
import com.qorvia.accountservice.dto.user.request.ProfileChangeRequest;
import com.qorvia.accountservice.service.auth.AuthService;
import com.qorvia.accountservice.service.follow.FollowService;
import com.qorvia.accountservice.service.jwt.JwtService;
import com.qorvia.accountservice.service.organizer.OrganizerService;
import com.qorvia.accountservice.service.user.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/account/user/")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;
    private final AuthService authService;
    private final OrganizerService organizerService;
    private final FollowService followService;
    private final JwtService jwtService;


    @GetMapping("/getUserData")
    public UserDTO getUserData(@RequestParam String email) {
        return userService.getUserDataByEmail(email);
    }

    @PutMapping("/passwordReset")
    public ResponseEntity<?> resetPassword(@RequestBody PasswordResetRequest resetRequest){
        log.info("password resetting by the user : {}", resetRequest.getEmail());
        return userService.resetPassword(resetRequest);
    }

    @PutMapping("/changeProfileInfo")
    public ResponseEntity<?> updateUserProfile(@RequestBody ProfileChangeRequest profileChangeRequest){
        log.info("updating the user profile by the user : {}", profileChangeRequest.getEmail());
     return userService.updateUserProfile(profileChangeRequest);
    }


    @GetMapping("/isUserActive/{email}")
    public ResponseEntity<Boolean> isUserActive(@PathVariable("email") String email){
        return authService.isUserActive(email);
    }

    @PostMapping("/followOrganizer")
    public ResponseEntity<?> followOrganizer(@RequestParam("organizerId") Long organizerId, HttpServletRequest servletRequest){
        Long userId = jwtService.getUserIdFormJwtToken(servletRequest);
        return followService.followAndUnfollowOrganizer(organizerId,userId);
    }

    @DeleteMapping("/unfollowOrganizer")
    public ResponseEntity<?> unfollowOrganizer(@RequestParam("organizerId") Long organizerId, HttpServletRequest servletRequest){
        Long userId = jwtService.getUserIdFormJwtToken(servletRequest);
        return followService.followAndUnfollowOrganizer(organizerId,userId);
    }



}
