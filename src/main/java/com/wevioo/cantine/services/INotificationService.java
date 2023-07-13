package com.wevioo.cantine.services;

import com.wevioo.cantine.entities.Notifications;
import com.wevioo.cantine.entities.User;

import java.util.List;

public interface INotificationService {
    void addNotification(String message);

    void addReservationNotification(String message, User user, String reservationStatus);

    void addNotificationToStaff(String message);

    void addNotificationToAdmin(String message);

    List<Notifications>  setUserNotificationsToSeen(Long id);

    List<Notifications> getNotificationsByUser(Long id);

}
