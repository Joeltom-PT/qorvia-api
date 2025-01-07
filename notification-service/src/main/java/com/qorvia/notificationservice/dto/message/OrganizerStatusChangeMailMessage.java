package com.qorvia.notificationservice.dto.message;

import com.qorvia.notificationservice.dto.request.OrganizerStatusChangeMailRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OrganizerStatusChangeMailMessage {
    private OrganizerStatusChangeMailRequest mailRequest;
}
