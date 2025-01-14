package com.qorvia.communicationservice.service;

import com.qorvia.communicationservice.dto.RoomAccessDTO;
import org.springframework.http.ResponseEntity;

public interface RoomService {
    ResponseEntity<String> allowAccess(RoomAccessDTO roomAccessDTO);

    ResponseEntity<String> getStreamKey(Long organizerId, String eventId);

    ResponseEntity<String> getStreamKeyForUser(Long userId, String eventId);
}
