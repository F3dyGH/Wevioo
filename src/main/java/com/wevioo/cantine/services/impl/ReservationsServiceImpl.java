package com.wevioo.cantine.services.impl;

import com.wevioo.cantine.entities.*;
import com.wevioo.cantine.enums.ReservationStatus;
import com.wevioo.cantine.enums.enumRole;
import com.wevioo.cantine.repositories.ReservationsRepository;
import com.wevioo.cantine.repositories.UserRepository;
import com.wevioo.cantine.services.INotificationService;
import com.wevioo.cantine.services.ReservationsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.*;

@Service
public class ReservationsServiceImpl implements ReservationsService {

    @Autowired
    ReservationsRepository reservationsRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    INotificationService notificationService;

    @Override
    public Reservations createReservation(User user, Menu menu, Starter starter) throws IllegalArgumentException {

        LocalDateTime startDateTime = menu.getDate().minusDays(1).atTime(18, 0);
        LocalDateTime endDateTime = menu.getDate().minusDays(1).atTime(23, 59);
        LocalDateTime startDateTimeToday = menu.getDate().atTime(0, 0);
        LocalDateTime endDateTimeToday = menu.getDate().atTime(10, 0);
        ReservationStatus[] reservationsStatus = {ReservationStatus.IN_PROCESS, ReservationStatus.TREATED};

        boolean hasReservationYesterday = reservationsRepository.existsByUserAndDateBetweenAndStarterIdIsNotNullAndReservationStatusIn(user, startDateTime, endDateTime, reservationsStatus);
        boolean hasReservationToday = reservationsRepository.existsByUserAndDateBetweenAndStarterIdIsNotNullAndReservationStatusIn(user, startDateTimeToday, endDateTimeToday, reservationsStatus);

        /*boolean hasCancelledReservation = reservationsRepository.existsByUserAndReservationStatusAndDateBetween(
                user, ReservationStatus.CANCELLED, reservationStartDateTime, reservationEndDateTime
        );*/

        if (hasReservationYesterday || hasReservationToday) { //check this method tkhallikech treservi akther men mara fel intervalle mtaa l wakt ama hata ki tfout l wakt twally dima 400 error treservich chouf kifeh tkhallih tkhallik treservi baaed l wakt fel interval
            //ki tcancel commande soit staff soit user rao lezem taawed aandel l ha9 tecmandi akther men mara fel interval taa l wakt
            throw new IllegalArgumentException("Menu reservation is allowed once a day");
        }

        if (user.getRoles().contains(enumRole.ROLE_STAFF) || user.getRoles().contains(enumRole.ROLE_ADMIN)) {
            throw new IllegalArgumentException("You are not allowed for this action.");

        } else {
            Reservations reservation = new Reservations();
            reservation.setMenu(menu);
            reservation.setUser(user);
            reservation.setDate(LocalDateTime.now());
            reservation.setReservationStatus(ReservationStatus.IN_PROCESS);
            reservation.setStarter(starter);
            reservation.setStaff(null);
            notificationService.addNotificationToStaff(reservation.getUser().getFirstname() + " " + reservation.getUser().getLastname() + " has made a reservation for " + reservation.getMenu().getName());
            return reservationsRepository.save(reservation);
        }
    }

    @Override
    public Reservations createFoodAndDrinksReservation(User user, FoodAndDrinks foodAndDrinks) throws IllegalArgumentException {

        if (user.getRoles().contains(enumRole.ROLE_STAFF) || user.getRoles().contains(enumRole.ROLE_ADMIN)) {
            throw new IllegalArgumentException("You are not allowed for this action.");
        } else {
            Reservations reservation = new Reservations();
            reservation.setUser(user);
            reservation.setFood(foodAndDrinks);
            reservation.setDate(LocalDateTime.now());
            reservation.setReservationStatus(ReservationStatus.IN_PROCESS);
            reservation.setStaff(null);
            notificationService.addNotificationToStaff(reservation.getUser().getFirstname() + " " + reservation.getUser().getLastname() + " has made a reservation for " + reservation.getFood().getName());
            return reservationsRepository.save(reservation);
        }
    }

    @Override
    public List<Reservations> getByStatus(ReservationStatus reservationStatus) {
        return reservationsRepository.findByReservationStatus(reservationStatus);
    }

    @Override
    public Map<LocalDate, List<Reservations>> userFilterByStatus(Long id, String reservationStatus) {
        List<Reservations> reservations = reservationsRepository.findByUserIdAndReservationStatusOrderByDateDesc(id, ReservationStatus.valueOf(reservationStatus));
        Map<LocalDate, List<Reservations>> filteredReservations = new HashMap<>();

        for (Reservations reservation : reservations) {
            LocalDate date = reservation.getDate().toLocalDate();
            List<Reservations> reservationsForDate = filteredReservations.getOrDefault(date, new ArrayList<>());
            reservationsForDate.add(reservation);
            filteredReservations.put(date, reservationsForDate);
        }

        return filteredReservations;
    }

    @Override
    public List<Reservations> getByUserAndDate(Long id, LocalDateTime date) {
        return reservationsRepository.findByUserAndDate(id, date);
    }

    @Override
    public List<Reservations> getByTodayDate() {
        LocalDateTime today = LocalDate.now().atTime(LocalTime.of(0, 0));
        LocalDateTime today6PM = today.withHour(18).withMinute(0);
        LocalDateTime tomorrow10AM = today.plusDays(1).withHour(10).withMinute(0);

        if (LocalDateTime.now().isBefore(today6PM)) {
            LocalDateTime yesterday6PM = today.minusDays(1).withHour(18).withMinute(0);
            LocalDateTime today10AM = today.withHour(10).withMinute(0);
            return reservationsRepository.findByReservationStatusAndDateBetweenOrderByDateDesc(ReservationStatus.IN_PROCESS, yesterday6PM, today6PM);
//            return reservationsRepository.findByReservationStatusAndDateBetweenOrderByDateDesc(ReservationStatus.IN_PROCESS, yesterday6PM, today10AM);
        } else {
            return reservationsRepository.findByReservationStatusAndDateBetweenOrderByDateDesc(ReservationStatus.IN_PROCESS, today6PM, tomorrow10AM);
        }
    }


    @Override
    public Reservations treatReservation(Long id, Long staffId) {
        User staff = userRepository.findById(staffId).orElse(null);
        Reservations reservation = reservationsRepository.findById(id).orElse(null);
        reservation.setReservationStatus(ReservationStatus.TREATED);
        reservation.setStaff(staff);
        if (reservation.getFood() != null) {
            notificationService.addNotification("Your Reservation for " + reservation.getFood().getName() + " is ready", reservation.getUser(), reservation.getReservationStatus().toString());
        }
        if (reservation.getMenu() != null) {
            notificationService.addNotification("Your Reservation for " + reservation.getMenu().getName() + " is ready", reservation.getUser(), reservation.getReservationStatus().toString());
        }
        Reservations updatedReservation = reservationsRepository.save(reservation);

        return updatedReservation;
    }

    @Override
    public Reservations cancelReservation(Long id, Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        Reservations reservation = reservationsRepository.findById(id).orElse(null);

        if (user != null && user.getRoles().stream().anyMatch(role -> role.getName() == enumRole.ROLE_STAFF)) {
            reservation.setReservationStatus(ReservationStatus.CANCELLED);
            reservation.setStaff(user);
            if (reservation.getFood() != null) {
                notificationService.addNotification("Your " + reservation.getFood().getName() + " reservation has been cancelled by the staff", reservation.getUser(), reservation.getReservationStatus().toString());
            }
            if (reservation.getMenu() != null) {
                notificationService.addNotification("Your " + reservation.getMenu().getName() + " reservation has been cancelled by the staff", reservation.getUser(), reservation.getReservationStatus().toString());
            }
        } else {
            reservation.setReservationStatus(ReservationStatus.CANCELLED);
            reservation.setStaff(null);
            /*if (reservation.getFoodAndDrinks() != null) {
                notificationService.addNotification("Your Reservation has been cancelled", reservation.getUser());
            }*/
        }

        return reservationsRepository.save(reservation);
    }

    @Override
    public List<Reservations> getByUserToday(Long id) {
        LocalDateTime currentDateTime = LocalDateTime.now(ZoneId.of("Africa/Tunis"));
        LocalDate today = currentDateTime.toLocalDate();
        LocalTime endTimeLimit = LocalTime.of(18, 0);
        LocalDateTime endDateTimeLimit = LocalDateTime.of(today.plusDays(1), endTimeLimit);

        User user = userRepository.findById(id).orElse(null);
        List<Reservations> reservations = reservationsRepository.findByUser(user);

        List<Reservations> reservationsToday = new ArrayList<>();
        for (Reservations reservation : reservations) {
            LocalDateTime reservationDateTime = reservation.getDate();
            if (reservationDateTime.toLocalDate().equals(today) &&
                    reservationDateTime.isBefore(endDateTimeLimit)) {
                reservationsToday.add(reservation);
            }
        }
        reservationsToday.sort(Comparator.comparing(Reservations::getDate).reversed());
        return reservationsToday;
    }


    @Override
    public Map<LocalDate, List<Reservations>> getReservationsByUserId(Long userId) {
        List<Reservations> reservations = reservationsRepository.findByUserId(userId);
        Map<LocalDate, List<Reservations>> reservationsByDate = new HashMap<>();

        for (Reservations reservation : reservations) {
            LocalDate date = reservation.getDate().toLocalDate();
            List<Reservations> reservationsForDate = reservationsByDate.getOrDefault(date, new ArrayList<>());
            reservationsForDate.add(reservation);
            reservationsByDate.put(date, reservationsForDate);
        }

        return reservationsByDate;
    }

}
