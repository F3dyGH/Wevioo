package com.wevioo.cantine.services.impl;

import com.wevioo.cantine.entities.*;
import com.wevioo.cantine.enums.ReservationStatus;
import com.wevioo.cantine.enums.enumRole;
import com.wevioo.cantine.repositories.DailyMenuReservationRepository;
import com.wevioo.cantine.repositories.UserRepository;
import com.wevioo.cantine.services.IDailyMenuReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DailyMenuReservationServiceImpl implements IDailyMenuReservationService {

    @Autowired
    DailyMenuReservationRepository dailyMenuReservationRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public DailyMenuReservation createReservation(User user, Menu menu, Starter starter) throws IllegalArgumentException {

        LocalDateTime currentTime = LocalDateTime.now(ZoneId.of("Africa/Tunis"));
        boolean hasReservation = dailyMenuReservationRepository.existsByUserAndDateBetween(
                user, currentTime.withHour(18).withMinute(0).withSecond(0),
                currentTime.plusDays(1).withHour(10).withMinute(40).withSecond(0)
        );

        if (hasReservation) { //check this method tkhallikech treservi akther men mara fel intervalle mtaa l wakt ama hata ki tfout l wakt twally dima 400 error treservich chouf kifeh tkhallih tkhallik treservi baaed l wakt fel interval
            //ki tcancel commande soit staff soit user rao lezem taawed aandel l ha9 tecmandi akther men mara fel interval taa l wakt
            throw new IllegalArgumentException("Menu reservation is allowed once a day");
        }
        if (user.getRoles().contains(enumRole.ROLE_STAFF) || user.getRoles().contains(enumRole.ROLE_ADMIN)) {
            throw new IllegalArgumentException("User not allowed for this action.");

        } else {
            DailyMenuReservation reservation = new DailyMenuReservation();
            reservation.setMenu(menu);
            reservation.setUser(user);
            reservation.setDate(LocalDateTime.now());
            reservation.setReservationStatus(ReservationStatus.IN_PROCESS);
            reservation.setStarter(starter);
            reservation.setStaff(null);

            return dailyMenuReservationRepository.save(reservation);
        }
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
    public Map<LocalDate, List<DailyMenuReservation>> userFilterByStatus(Long id, String reservationStatus) {
//        return dailyMenuReservationRepository.findByUser_IdAndReservationStatusOrderByDateDesc(id, ReservationStatus.valueOf(reservationStatus));
        List<DailyMenuReservation> reservations = dailyMenuReservationRepository.findByUser_IdAndReservationStatusOrderByDateDesc(id, ReservationStatus.valueOf(reservationStatus));
        Map<LocalDate, List<DailyMenuReservation>> filteredReservations = new HashMap<>();

        for (DailyMenuReservation reservation : reservations) {
            LocalDate date = reservation.getDate().toLocalDate();
            List<DailyMenuReservation> reservationsForDate = filteredReservations.getOrDefault(date, new ArrayList<>());
            reservationsForDate.add(reservation);
            filteredReservations.put(date, reservationsForDate);
        }

        return filteredReservations;
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

    @Override
    public DailyMenuReservation treatReservation(Long id, Long staffId) {
        User staff = userRepository.findById(staffId).orElse(null);
        DailyMenuReservation reservation = dailyMenuReservationRepository.findById(id).orElse(null);
        reservation.setReservationStatus(ReservationStatus.TREATED);
        reservation.setStaff(staff);
        DailyMenuReservation updatedReservation = dailyMenuReservationRepository.save(reservation);

        return updatedReservation;
    }

    @Override
    public DailyMenuReservation cancelReservation(Long id, Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        DailyMenuReservation reservation = dailyMenuReservationRepository.findById(id).orElse(null);
        reservation.setReservationStatus(ReservationStatus.CANCELLED);
        if (user.getRoles().contains(enumRole.ROLE_USER)) {
            reservation.setStaff(null);
        } else {
            reservation.setStaff(user);
        }
        return dailyMenuReservationRepository.save(reservation);
    }

    @Override
    public boolean hasReservation(User user) {
        List<DailyMenuReservation> existingReservations = dailyMenuReservationRepository.findAll();

        for (DailyMenuReservation reservation : existingReservations) {
            if (reservation.getUser() == user) {
                return true;
            }
        }
        return false;
    }

    @Override
    public List<DailyMenuReservation> getByUserToday(Long id){
        LocalDate today = LocalDate.now(ZoneId.of("Africa/Tunis"));
        LocalDateTime startTime = LocalDateTime.of(today, LocalTime.MIN);
        LocalDateTime endTime = LocalDateTime.of(today, LocalTime.MAX);
        User  user = userRepository.findById(id).orElse(null);
        return dailyMenuReservationRepository.findByUserAndDateBetween(user,startTime,endTime);
    }

    @Override
    public Map<LocalDate, List<DailyMenuReservation>> getReservationsByUserId(Long userId) {
        List<DailyMenuReservation> reservations = dailyMenuReservationRepository.findByUser_Id(userId);
        Map<LocalDate, List<DailyMenuReservation>> reservationsByDate = new HashMap<>();

        for (DailyMenuReservation reservation : reservations) {
            LocalDate date = reservation.getDate().toLocalDate();
            List<DailyMenuReservation> reservationsForDate = reservationsByDate.getOrDefault(date, new ArrayList<>());
            reservationsForDate.add(reservation);
            reservationsByDate.put(date, reservationsForDate);
        }

        return reservationsByDate;
    }
}
