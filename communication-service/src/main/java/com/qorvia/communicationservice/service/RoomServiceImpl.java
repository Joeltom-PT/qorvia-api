package com.qorvia.communicationservice.service;

import com.qorvia.communicationservice.dto.RoomAccessDTO;
import com.qorvia.communicationservice.model.Event;
import com.qorvia.communicationservice.model.EventInfo;
import com.qorvia.communicationservice.model.RoomAccess;
import com.qorvia.communicationservice.model.Schedules;
import com.qorvia.communicationservice.repository.EventRepository;
import com.qorvia.communicationservice.repository.RoomAccessRepository;
import com.qorvia.communicationservice.repository.SchedulesRepository;
import com.qorvia.communicationservice.socket.RoomManager;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {

    private final RoomAccessRepository roomAccessRepository;
    private final SchedulesRepository schedulesRepository;
    private final EventRepository eventRepository;

    @Override
    public ResponseEntity<String> allowAccess(RoomAccessDTO roomAccessDTO) {
        Long userId = roomAccessDTO.getUserId();
        String eventId = roomAccessDTO.getEventId();
        String userEmail = roomAccessDTO.getUserEmail();

        Optional<RoomAccess> roomAccess = roomAccessRepository.findByUserIdAndEventId(userId, eventId);

        if (roomAccess.isPresent()) {
            return ResponseEntity.ok("Access already granted.");
        }

        RoomAccess newRoomAccess = new RoomAccess();
        newRoomAccess.setId(UUID.randomUUID());
        newRoomAccess.setUserId(userId);
        newRoomAccess.setEventId(eventId);
        newRoomAccess.setUserEmail(userEmail);

        roomAccessRepository.save(newRoomAccess);

        return ResponseEntity.ok("Access granted successfully.");
    }

    @Override
    public ResponseEntity<String> getStreamKey(Long organizerId, String eventId) {
        Optional<Event> optionalEvent = eventRepository.findByEventId(eventId);
        if (optionalEvent.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Event not found");
        }
        Event event = optionalEvent.get();

        EventInfo eventInfo = event.getEventInfo();

        if (!Objects.equals(eventInfo.getOrganizerId(), organizerId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("You are not authorized to access this stream key");
        }

        return ResponseEntity.ok(event.getSteamingId());
    }

    @Override
    public ResponseEntity<String> getStreamKeyForUser(Long userId, String eventId) {
        Optional<RoomAccess> roomAccess = roomAccessRepository.findByUserIdAndEventId(userId, eventId);
        if (roomAccess.isPresent()) {
            Optional<Event> optionalEvent = eventRepository.findByEventId(eventId);
            if (optionalEvent.isPresent()) {
                Event event = optionalEvent.get();
                return ResponseEntity.ok(event.getSteamingId());
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Event not found");
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body("You do not have access to the stream for this event");
    }



}
