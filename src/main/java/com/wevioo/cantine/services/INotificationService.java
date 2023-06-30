package com.wevioo.cantine.services;

import com.wevioo.cantine.entities.Notifications;
import com.wevioo.cantine.entities.Reservations;
import com.wevioo.cantine.entities.User;

import java.time.LocalDateTime;
import java.util.List;

public interface INotificationService {
    void addNotification(String message, User user, String reservationStatus);

    void addNotificationToStaff(String message);

    List<Notifications>  setUserNotificationsToSeen(Long id);

    List<Notifications> getNotificationsByUser(Long id);

    List<Notifications> getNewNotificationsSince(LocalDateTime lastPollTimestamp);
}
