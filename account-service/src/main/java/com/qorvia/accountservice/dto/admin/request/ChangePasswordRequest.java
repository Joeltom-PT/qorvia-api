package com.qorvia.accountservice.dto.admin.request;

import lombok.Data;

@Data
public class ChangePasswordRequest {
    private String currentPass;
    private String newPass;
}
