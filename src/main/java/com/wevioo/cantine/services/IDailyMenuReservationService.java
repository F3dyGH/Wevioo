package com.wevioo.cantine.services;


import com.wevioo.cantine.entities.DailyMenuReservation;
import com.wevioo.cantine.entities.Menu;
import com.wevioo.cantine.entities.User;
import com.wevioo.cantine.enums.ReservationStatus;

import java.time.LocalDateTime;
import java.util.List;

public interface IDailyMenuReservationService {
        DailyMenuReservation createReservation(User user, Menu menu);
        List<DailyMenuReservation> getAllDailyMenuReservation();

        List<DailyMenuReservation> getByStatus(ReservationStatus reservationStatus);
        List<DailyMenuReservation> getByUser(Long id);

        List<DailyMenuReservation> getByUserAndDate(Long id, LocalDateTime date);

        List<DailyMenuReservation> getByTodayDate();
}
