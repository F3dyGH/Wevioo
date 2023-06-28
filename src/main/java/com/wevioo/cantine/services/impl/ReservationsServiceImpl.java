package com.wevioo.cantine.services.impl;

import com.wevioo.cantine.entities.*;
import com.wevioo.cantine.enums.ReservationStatus;
import com.wevioo.cantine.enums.enumRole;
import com.wevioo.cantine.repositories.ReservationsRepository;
import com.wevioo.cantine.repositories.FoodAndDrinksRepository;
import com.wevioo.cantine.repositories.UserRepository;
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
    private FoodAndDrinksRepository foodAndDrinksRepository;

    @Override
    public Reservations createReservation(User user, Menu menu, Starter starter) throws IllegalArgumentException {

        LocalDateTime reservationStartDateTime = menu.getDate().minusDays(1).atTime(18,0);
        LocalDateTime reservationEndDateTime = menu.getDate().atTime(10,0);

        boolean hasReservation = reservationsRepository.existsByUserAndDateBetween(user, reservationStartDateTime, reservationEndDateTime);

        boolean hasCancelledReservation = reservationsRepository.existsByUserAndReservationStatusAndDateBetween(
                user, ReservationStatus.CANCELLED, reservationStartDateTime, reservationEndDateTime
        );

        if (hasReservation && !hasCancelledReservation) { //check this method tkhallikech treservi akther men mara fel intervalle mtaa l wakt ama hata ki tfout l wakt twally dima 400 error treservich chouf kifeh tkhallih tkhallik treservi baaed l wakt fel interval
            //ki tcancel commande soit staff soit user rao lezem taawed aandel l ha9 tecmandi akther men mara fel interval taa l wakt
            throw new IllegalArgumentException("Menu reservation is allowed once a day");//update: after trying it still not working, should look for it another time
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
            reservation.setFoodAndDrinks(foodAndDrinks);
            reservation.setDate(LocalDateTime.now());
            reservation.setReservationStatus(ReservationStatus.IN_PROCESS);
            reservation.setStaff(null);

            return reservationsRepository.save(reservation);
        }
    }


    @Override
    public List<Reservations> getAllDailyMenuReservation() {
        return reservationsRepository.findAll();
    }

    @Override
    public List<Reservations> getByStatus(ReservationStatus reservationStatus) {
        return reservationsRepository.findByReservationStatus(reservationStatus);
    }

    @Override
    public Map<LocalDate, List<Reservations>> userFilterByStatus(Long id, String reservationStatus) {
//        return dailyMenuReservationRepository.findByUser_IdAndReservationStatusOrderByDateDesc(id, ReservationStatus.valueOf(reservationStatus));
        List<Reservations> reservations = reservationsRepository.findByUser_IdAndReservationStatusOrderByDateDesc(id, ReservationStatus.valueOf(reservationStatus));
        Map<LocalDate, List<Reservations>> filteredReservations = new HashMap<>();

        for (Reservations reservation : reservations) {
            LocalDate date = reservation.getDate().toLocalDate();
            List<Reservations> reservationsForDate = filteredReservations.getOrDefault(date, new ArrayList<>());
            reservationsForDate.add(reservation);
            filteredReservations.put(date, reservationsForDate);
        }

        return filteredReservations;
    }

   /* @Override
    public List<Reservations> getByUser(Long id) {
        return dailyMenuReservationRepository.findByUser(id);
    }
*/
    @Override
    public List<Reservations> getByUserAndDate(Long id, LocalDateTime date) {
        return reservationsRepository.findByUserAndDate(id, date);
    }

    @Override
    public List<Reservations> getByTodayDate() {
       // return reservationsRepository.findByTodayDate();
        LocalDateTime yesterdayHour = LocalDate.now().minusDays(1).atTime(LocalTime.of(18, 0));
        LocalDateTime todayMaxHour = LocalDate.now().atTime(LocalTime.of(18, 0));

        return reservationsRepository.findReservationsBetweenYesterdayAndToday(yesterdayHour, todayMaxHour);
    }

    @Override
    public Reservations treatReservation(Long id, Long staffId) {
        User staff = userRepository.findById(staffId).orElse(null);
        Reservations reservation = reservationsRepository.findById(id).orElse(null);
        reservation.setReservationStatus(ReservationStatus.TREATED);
        reservation.setStaff(staff);
        Reservations updatedReservation = reservationsRepository.save(reservation);

        return updatedReservation;
    }

    @Override
    public Reservations cancelReservation(Long id, Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        Reservations reservation = reservationsRepository.findById(id).orElse(null);
        reservation.setReservationStatus(ReservationStatus.CANCELLED);
        if (user.getRoles().contains(enumRole.ROLE_USER)) {
            reservation.setStaff(null);
        } else {
            reservation.setStaff(user);
        }
        return reservationsRepository.save(reservation);
    }

    @Override
    public boolean hasReservation(User user) {
        List<Reservations> existingReservations = reservationsRepository.findAll();

        for (Reservations reservation : existingReservations) {
            if (reservation.getUser() == user) {
                return true;
            }
        }
        return false;
    }

    /*@Override
    public List<Reservations> getByUserToday(Long id){
        LocalDate today = LocalDate.now(ZoneId.of("Africa/Tunis"));
        LocalDateTime startTime = LocalDateTime.of(today, LocalTime.MIN);
        LocalDateTime endTime = LocalDateTime.of(today, LocalTime.MAX);
        User user = userRepository.findById(id).orElse(null);
        return dailyMenuReservationRepository.findByUserAndDateBetween(user,startTime,endTime);
    }*/

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
        List<Reservations> reservations = reservationsRepository.findByUser_Id(userId);
        Map<LocalDate, List<Reservations>> reservationsByDate = new HashMap<>();

        for (Reservations reservation : reservations) {
            LocalDate date = reservation.getDate().toLocalDate();
            List<Reservations> reservationsForDate = reservationsByDate.getOrDefault(date, new ArrayList<>());
            reservationsForDate.add(reservation);
            reservationsByDate.put(date, reservationsForDate);
        }

        return reservationsByDate;
    }

    @Override
    public Reservations createOtherReservations(User user, FoodAndDrinks item){
        LocalDateTime reservationTime = LocalDateTime.now(ZoneId.of("Africa/Tunis"));
       /* Optional<FoodAndDrinks> foodAndDrinksOptional = foodAndDrinksRepository.findById(item.getId());
        if (foodAndDrinksOptional.isPresent()) {
            FoodAndDrinks foodAndDrinks = foodAndDrinksOptional.get();
            int remainingQuantity = foodAndDrinks.getQuantity();
            if (remainingQuantity > 0) {
                foodAndDrinks.setQuantity(remainingQuantity - 1);
                foodAndDrinksRepository.save(foodAndDrinks);
                return ResponseEntity.ok().build();
            } else {
                return ResponseEntity.badRequest().body("No more available quantity for this item.");
            }
        } else {
            return ResponseEntity.notFound().build();
        }*/
        Reservations reservation = new Reservations();
        reservation.setUser(user);
        reservation.setFoodAndDrinks(item);
        reservation.setDate(LocalDateTime.now(ZoneId.of("Africa/Tunis")));
        return reservationsRepository.save(reservation);
    }
}
