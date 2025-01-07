package com.qorvia.accountservice.dto.organizer;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class OrganizerSettingsDTO {
    @JsonProperty(namespace = "isApprovalAllowed")
    private boolean isApprovalAllowed;
}
