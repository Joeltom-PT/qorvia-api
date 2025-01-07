package com.qorvia.accountservice.service.follow;

import org.springframework.http.ResponseEntity;

public interface FollowService {
    ResponseEntity<?> followAndUnfollowOrganizer(Long organizerId, Long userId);
}
