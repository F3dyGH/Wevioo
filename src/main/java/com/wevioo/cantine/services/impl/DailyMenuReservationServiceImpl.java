package com.wevioo.cantine.services.impl;

import com.wevioo.cantine.entities.DailyMenuReservation;
import com.wevioo.cantine.entities.Menu;
import com.wevioo.cantine.entities.Starter;
import com.wevioo.cantine.entities.User;
import com.wevioo.cantine.enums.ReservationStatus;
import com.wevioo.cantine.enums.enumRole;
import com.wevioo.cantine.repositories.DailyMenuReservationRepository;
import com.wevioo.cantine.repositories.UserRepository;
import com.wevioo.cantine.services.IDailyMenuReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

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
        return dailyMenuReservationRepository.save(reservation);
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
}
