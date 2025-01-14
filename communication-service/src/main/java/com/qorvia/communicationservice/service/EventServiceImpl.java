package com.qorvia.communicationservice.service;

import com.qorvia.communicationservice.dto.ScheduleEventDTO;
import com.qorvia.communicationservice.model.Event;
import com.qorvia.communicationservice.model.EventInfo;
import com.qorvia.communicationservice.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService{

    private final EventRepository eventRepository;

    private final SchedulerService schedulerService;

    @Override
    public ResponseEntity<String> scheduleEvent(ScheduleEventDTO eventDTO) {
        try {
            String roomId = UUID.randomUUID().toString();

            createEvent(eventDTO, roomId);

            schedulerService.schedule(eventDTO);

            return ResponseEntity.ok("Event scheduled successfully.");

        } catch (Exception e) {
            e.printStackTrace();

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while scheduling the event.");
        }
    }

    private void createEvent(ScheduleEventDTO eventDTO, String roomId) {
        try {
            Optional<Event> existingEvent = eventRepository.findByEventId(eventDTO.getEventId());
            existingEvent.ifPresent(eventRepository::delete);

            EventInfo eventInfo = new EventInfo();
            eventInfo.setImageUrl(eventDTO.getImageUrl());
            eventInfo.setName(eventDTO.getName());
            eventInfo.setOrganizerId(eventDTO.getOrganizerId());

            Event event = Event.builder()
                    .id(UUID.randomUUID())
                    .eventId(eventDTO.getEventId())
                    .steamingId(roomId)
                    .eventInfo(eventInfo)
                    .isStreaming(false)
                    .build();

            eventRepository.save(event);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("An error occurred while creating the event.");
        }
    }

}
