package com.qorvia.accountservice.dto.message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class VerifyOtpMessage {
    private String email;
    private String otp;
}