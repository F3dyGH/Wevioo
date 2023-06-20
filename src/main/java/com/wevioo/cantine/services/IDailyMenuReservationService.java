package com.wevioo.cantine.services;


import com.wevioo.cantine.entities.DailyMenuReservation;
import com.wevioo.cantine.entities.Menu;
import com.wevioo.cantine.entities.Starter;
import com.wevioo.cantine.entities.User;
import com.wevioo.cantine.enums.ReservationStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface IDailyMenuReservationService {
        DailyMenuReservation createReservation(User user, Menu menu, Starter starter);
        List<DailyMenuReservation> getAllDailyMenuReservation();

        List<DailyMenuReservation> getByStatus(ReservationStatus reservationStatus);
        Map<LocalDate, List<DailyMenuReservation>> userFilterByStatus(Long id, String reservationStatus);
        List<DailyMenuReservation> getByUser(Long id);

        List<DailyMenuReservation> getByUserAndDate(Long id, LocalDateTime date);

        List<DailyMenuReservation> getByTodayDate();

        DailyMenuReservation treatReservation(Long id,  Long staffId);

        DailyMenuReservation cancelReservation(Long id,  Long staffId);

        boolean hasReservation(User user);

        List<DailyMenuReservation> getByUserToday(Long id);

        Map<LocalDate, List<DailyMenuReservation>> getReservationsByUserId(Long userId);
}
