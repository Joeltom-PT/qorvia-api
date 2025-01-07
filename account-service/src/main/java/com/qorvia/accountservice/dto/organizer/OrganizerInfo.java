package com.qorvia.accountservice.dto.organizer;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrganizerInfo {
    private Long id;
    private String name;
    private String description;
    @JsonProperty("isFollowing")
    private boolean isFollowing;
    private String imageUrl;
    private OrganizerStatsDTO stats;

    @Data
    @Builder
    public static class OrganizerStatsDTO {
        private int followers;
        private int posts;
        private int events;
    }
}
