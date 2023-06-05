package com.wevioo.cantine.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class NotificationsController {
    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/notifications")
    public void subscribeNotifications() {
        // Method to handle the subscription request
    }

    public void sendNotificationToStaff(String message) {
        messagingTemplate.convertAndSend("/topic/notifications", message);
    }
}
