package com.qorvia.accountservice.dto.organizer;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrganizerShortInfo {

    private Long id;
    private String name;
    private String imgUrl;
    private int totalFollowers;
    private int totalEvents;
    @JsonProperty("isFollowing")
    private boolean isFollowing;

}
