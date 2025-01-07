package com.qorvia.accountservice.service.auth;

import com.qorvia.accountservice.dto.auth.request.*;
import com.qorvia.accountservice.dto.organizer.OrganizerDTO;
import com.qorvia.accountservice.dto.organizer.OrganizerLoginRequest;
import com.qorvia.accountservice.dto.organizer.OrganizerRegisterRequest;
import com.qorvia.accountservice.dto.organizer.OrganizerVerificationTokenRequest;
import com.qorvia.accountservice.dto.auth.response.OrganizerVerificationResponse;
import com.qorvia.accountservice.dto.user.UserDTO;
import com.qorvia.accountservice.dto.response.ApiResponse;
import com.qorvia.accountservice.dto.auth.response.OtpResponse;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;

public interface AuthService {
    UserDTO createAccount(RegisterRequest registerRequest);

    ResponseEntity<ApiResponse<Object>> loginUser(LoginRequest loginRequest, HttpServletResponse response);

    ResponseEntity<ApiResponse<OtpResponse>> verifyEmail(OtpRequest otpRequest,HttpServletResponse response);

    String googleAuthentication(UserDTO userDTO, HttpServletResponse servletResponse) throws Exception;

    ResponseEntity<ApiResponse<Object>> logout(HttpServletResponse response);

    ResponseEntity<ApiResponse<String>> registerOrganizer(OrganizerRegisterRequest registerRequest);

    ResponseEntity<ApiResponse<OrganizerDTO>> loginOrganizer(OrganizerLoginRequest loginRequest, HttpServletResponse response);

    ResponseEntity<String> organizerEmailVerificationSendRequest(String email);

    ResponseEntity<ApiResponse<OrganizerVerificationResponse>> organizerVerificationTokenVerify(OrganizerVerificationTokenRequest request, HttpServletResponse response);

    ResponseEntity<?> forgotPassword(ForgotPasswordRequest forgotPasswordRequest);

    ResponseEntity<?> forgotPasswordReset(ForgotPasswordResetRequest passwordResetRequest);

    ResponseEntity<Boolean> isUserActive(String email);
}
