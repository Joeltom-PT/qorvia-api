package com.qorvia.communicationservice.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class WebRTCController {

    @MessageMapping("/webrtc/offer")
    @SendTo("/topic/offer")
    public String offer(String offer) {
        return offer;
    }

    @MessageMapping("/webrtc/answer")
    @SendTo("/topic/answer")
    public String answer(String answer) {
        return answer;
    }

    @MessageMapping("/webrtc/candidate")
    @SendTo("/topic/candidate")
    public String candidate(String candidate) {
        return candidate;
    }
}
