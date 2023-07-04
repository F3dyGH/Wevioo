package com.wevioo.cantine.services.impl;

import com.wevioo.cantine.entities.Notifications;
import com.wevioo.cantine.entities.User;
import com.wevioo.cantine.enums.enumRole;
import com.wevioo.cantine.repositories.NotificationsRepository;
import com.wevioo.cantine.repositories.UserRepository;
import com.wevioo.cantine.services.INotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Service
public class NotificationServiceImpl implements INotificationService {

    @Autowired
    NotificationsRepository notificationsRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    public void addNotification(String message, User user, String reservationStatus) {
        Notifications notification = new Notifications();
        notification.setMessage(message);
        notification.setUser(user);
        notification.setReservationStatus(reservationStatus);
        notification.setDateTime(LocalDateTime.now(ZoneId.of("Africa/Tunis")));
        notification.setSeen(false);
        notificationsRepository.save(notification);
    }

    @Override
    public void addNotificationToStaff(String message) {
        List<User> staffUsers = userRepository.findAll();
        for (User staffUser : staffUsers) {
            if (staffUser.getRoles().stream().anyMatch(role -> role.getName() == enumRole.ROLE_STAFF)) {
                addNotification(message, staffUser, "");
            }
        }
    }

    @Override
    public List<Notifications> setUserNotificationsToSeen(Long id){
        User user = userRepository.findById(id).orElse(null);
        List<Notifications> notifications = notificationsRepository.findByUser(user);
        for (Notifications notification : notifications){
            notification.setSeen(true);
        }
        return notificationsRepository.saveAll(notifications);
    }

    @Override
    public List<Notifications> getNotificationsByUser(Long id) {
        List<Notifications> notificationsList = notificationsRepository.findTop5ByUserIdOrderByDateTimeDesc(id);
        return notificationsList;
    }

    @Override
    public List<Notifications> getNewNotificationsSince(LocalDateTime lastPollTimestamp) {
        List<Notifications> newNotifications = notificationsRepository.findNewNotificationsSince(lastPollTimestamp);
        return newNotifications;
    }
}
