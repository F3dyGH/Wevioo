package com.wevioo.cantine.services.impl;

import com.wevioo.cantine.controllers.NotificationsController;
import com.wevioo.cantine.entities.DailyMenuReservation;
import com.wevioo.cantine.entities.Menu;
import com.wevioo.cantine.entities.User;
import com.wevioo.cantine.enums.ReservationStatus;
import com.wevioo.cantine.repositories.DailyMenuReservationRepository;
import com.wevioo.cantine.services.IDailyMenuReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.List;

@Service
public class DailyMenuReservationServiceImpl implements IDailyMenuReservationService {

    @Autowired
    DailyMenuReservationRepository dailyMenuReservationRepository;
    @Autowired
    private NotificationsController notificationController;

    @Override
    public DailyMenuReservation createReservation(User user, Menu menu) {

        DailyMenuReservation reservation = new DailyMenuReservation();

        reservation.setMenu(menu);
        reservation.setUser(user);
        reservation.setDate(LocalDateTime.now(ZoneId.of("Africa/Tunis")));
        reservation.setReservationStatus(ReservationStatus.IN_PROCESS);
        sendReservationNotification(reservation);
        return dailyMenuReservationRepository.save(reservation);

    }

    @Override
    public List<DailyMenuReservation> getAllDailyMenuReservation() {
        return dailyMenuReservationRepository.findAll();
    }

    @Override
    public List<DailyMenuReservation> getByStatus(ReservationStatus reservationStatus) {
        return dailyMenuReservationRepository.findByReservationStatus(reservationStatus);
    }

    @Override
    public List<DailyMenuReservation> getByUser(Long id) {
        return dailyMenuReservationRepository.findByUser(id);
    }

    @Override
    public List<DailyMenuReservation> getByUserAndDate(Long id, LocalDateTime date) {
        return dailyMenuReservationRepository.findByUserAndDate(id, date);
    }

    @Override
    public List<DailyMenuReservation> getByTodayDate() {
        return dailyMenuReservationRepository.findByTodayDate();
    }

    private void sendReservationNotification(DailyMenuReservation reservation) {
        String message = "A new reservation has been made by " + reservation.getUser().getFirstname() + " " + reservation.getUser().getLastname();
        notificationController.sendNotificationToStaff(message);
    }
}
