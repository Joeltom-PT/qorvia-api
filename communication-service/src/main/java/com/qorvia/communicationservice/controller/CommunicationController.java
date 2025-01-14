package com.qorvia.communicationservice.controller;

import com.qorvia.communicationservice.dto.RoomAccessDTO;
import com.qorvia.communicationservice.dto.ScheduleEventDTO;
import com.qorvia.communicationservice.security.RequireRole;
import com.qorvia.communicationservice.security.Roles;
import com.qorvia.communicationservice.service.EventService;
import com.qorvia.communicationservice.service.RoomService;
import com.qorvia.communicationservice.service.jwt.JwtService;
import com.qorvia.communicationservice.socket.RoomManager;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/communication")
@RequiredArgsConstructor
public class CommunicationController {

    private final EventService eventService;
    private final RoomService roomService;
    private final RoomManager roomManager;
    private final JwtService jwtService;

    @PostMapping("/schedule-event")
    public ResponseEntity<String> scheduleEvent(@RequestBody ScheduleEventDTO eventDTO){
        return eventService.scheduleEvent(eventDTO);
    }

    @PostMapping("/allow-room")
    public ResponseEntity<String> allowRoom(@RequestBody RoomAccessDTO roomAccessDTO){
        return roomService.allowAccess(roomAccessDTO);
    }

    @GetMapping("/validate/{eventId}")
    public ResponseEntity<Boolean> validateAccess(HttpServletRequest servletRequest, @PathVariable String eventId) {
        Long userId = jwtService.getUserIdFormRequest(servletRequest);
        boolean hasAccess = roomManager.hasAccess(eventId, userId);
        return ResponseEntity.ok(hasAccess);
    }

    @GetMapping("/get-streaming-key/organizer/{eventId}")
    @RequireRole(role = Roles.ORGANIZER)
    public ResponseEntity<String> getStreamingKeyForOrganizer(@PathVariable("eventId") String eventId, HttpServletRequest servletRequest) {
        Long organizerId = jwtService.getUserIdFormRequest(servletRequest);
        return roomService.getStreamKey(organizerId, eventId);
    }

    @GetMapping("/get-streaming-key/user/{eventId}")
    @RequireRole(role = Roles.USER)
    public ResponseEntity<String> getStreamingKeyForUser(@PathVariable("eventId") String eventId, HttpServletRequest servletRequest) {
        Long userId = jwtService.getUserIdFormRequest(servletRequest);
        return roomService.getStreamKeyForUser(userId, eventId);
    }


}
