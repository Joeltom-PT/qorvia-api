package com.qorvia.accountservice.dto.organizer;

import com.qorvia.accountservice.model.Roles;
import com.qorvia.accountservice.model.VerificationStatus;
import com.qorvia.accountservice.model.organizer.RegisterRequestStatus;
import com.qorvia.accountservice.model.organizer.OrganizerStatus;
import lombok.*;

import java.math.BigInteger;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrganizerProfileDTO {
    private Long id;
    private String organizationName;
    private BigInteger phone;
    private String website;
    private String address;
    private String address2;
    private String city;
    private String country;
    private String state;
    private String facebook;
    private String instagram;
    private String twitter;
    private String linkedin;
    private String youtube;
    private String profileImage;
    private String about;
    private boolean isFollowing;
    private int totalEvents;
    private int totalFollowers;
}
