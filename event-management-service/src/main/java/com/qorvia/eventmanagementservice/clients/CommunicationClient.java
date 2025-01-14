package com.qorvia.eventmanagementservice.clients;

import com.qorvia.eventmanagementservice.dto.RoomAccessDTO;
import com.qorvia.eventmanagementservice.dto.ScheduleEventDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "COMMUNICATION-SERVICE", url = "http://localhost:8882")
public interface CommunicationClient {

    @PostMapping("/communication/allow-room")
    public ResponseEntity<String> allowRoom(@RequestBody RoomAccessDTO roomAccessDTO);

    @PostMapping("/communication/schedule-event")
    public ResponseEntity<String> scheduleEvent(@RequestBody ScheduleEventDTO eventDTO);

}
