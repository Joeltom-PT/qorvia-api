package com.qorvia.communicationservice.controller;

import com.qorvia.communicationservice.dto.Comment;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.util.concurrent.atomic.AtomicInteger;

@Controller
public class ChatController {

    private AtomicInteger viewerCount = new AtomicInteger(0);

    @MessageMapping("/comment")
    @SendTo("/topic/comments")
    public Comment comment(Comment comment) {
        return comment;
    }

    @MessageMapping("/viewer")
    @SendTo("/topic/viewers")
    public int updateViewerCount(int delta) {
        return viewerCount.addAndGet(delta);
    }
}
