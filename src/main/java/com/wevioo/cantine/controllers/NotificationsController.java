package com.wevioo.cantine.controllers;

import com.wevioo.cantine.entities.Notifications;
import com.wevioo.cantine.repositories.UserRepository;
import com.wevioo.cantine.services.INotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notifications")
public class NotificationsController {

    @Autowired
    INotificationService notificationService;

    @Autowired
    UserRepository userRepository;

    @GetMapping("/{userId}")
    public ResponseEntity<List<Notifications>> getNotificationsByUser(@PathVariable("userId") Long id){
        List<Notifications> notifications = notificationService.getNotificationsByUser(id);
        if(notifications != null) {
            return ResponseEntity.ok(notifications);
        }else {
            return ResponseEntity.notFound().build();
        }
    }
   @PutMapping("/{userId}/seen")
    public ResponseEntity<List<Notifications>> setUserNotifToSeen(@PathVariable("userId") Long id){
       List<Notifications> notifications =  notificationService.setUserNotificationsToSeen(id);
        return ResponseEntity.ok(notifications);
   }
}
