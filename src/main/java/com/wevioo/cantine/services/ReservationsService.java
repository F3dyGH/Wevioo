package com.wevioo.cantine.services;


import com.wevioo.cantine.entities.*;
import com.wevioo.cantine.enums.ReservationStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface ReservationsService {
        Reservations createReservation(User user, Menu menu, Starter starter);

        Reservations createFoodAndDrinksReservation(User user, FoodAndDrinks foodAndDrinks) throws IllegalArgumentException;

        //Reservations createBreakfastReservation(User user, Menu menu, Starter starter);
        List<Reservations> getAllDailyMenuReservation();

        List<Reservations> getByStatus(ReservationStatus reservationStatus);
        Map<LocalDate, List<Reservations>> userFilterByStatus(Long id, String reservationStatus);
//        List<Reservations> getByUser(Long id);

        List<Reservations> getByUserAndDate(Long id, LocalDateTime date);

        List<Reservations> getByTodayDate();

        Reservations treatReservation(Long id, Long staffId);

        Reservations cancelReservation(Long id, Long staffId);

        boolean hasReservation(User user);

        List<Reservations> getByUserToday(Long id);

        Map<LocalDate, List<Reservations>> getReservationsByUserId(Long userId);

    Reservations createOtherReservations(User user, FoodAndDrinks item);
}
