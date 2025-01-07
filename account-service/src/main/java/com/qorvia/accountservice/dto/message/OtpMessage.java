package com.qorvia.accountservice.dto.message;

import com.qorvia.accountservice.dto.admin.request.OrganizerStatusChangeMailRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OtpMessage {
    private String email;
}
