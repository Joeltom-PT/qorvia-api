package com.qorvia.communicationservice.service;

import com.qorvia.communicationservice.dto.ScheduleEventDTO;
import org.springframework.http.ResponseEntity;

public interface EventService {
    ResponseEntity<String> scheduleEvent(ScheduleEventDTO eventDTO);
}
