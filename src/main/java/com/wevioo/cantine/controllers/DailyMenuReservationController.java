package com.wevioo.cantine.controllers;

import com.wevioo.cantine.config.LocalDateConverter;
import com.wevioo.cantine.entities.*;
import com.wevioo.cantine.enums.ReservationStatus;
import com.wevioo.cantine.enums.enumRole;
import com.wevioo.cantine.repositories.MenuRepository;
import com.wevioo.cantine.repositories.StarterRepository;
import com.wevioo.cantine.repositories.UserRepository;
import com.wevioo.cantine.services.IDailyMenuReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Controller
@RequestMapping("/menuReservation")
public class DailyMenuReservationController {

    @Autowired
    IDailyMenuReservationService dailyMenuReservationService;
    @Autowired
    MenuRepository menuRepository;

    @Autowired
    StarterRepository starterRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    LocalDateConverter localDateConverter;


    @PreAuthorize("hasRole('USER')")
    @PostMapping("/create")
    ResponseEntity<DailyMenuReservation> create(@RequestParam Long userId, @RequestParam Long menuId, @RequestParam Long starterId) {
        User user = userRepository.findById(userId).orElse(null);
        Menu menu = menuRepository.findById(menuId).orElse(null);
        Starter starter = starterRepository.findById(starterId).orElse(null);
        LocalDateTime currentTime = LocalDateTime.now(ZoneId.of("Africa/Tunis"));

        if (currentTime.getHour() < 18 && currentTime.getHour() >= 10) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        } else {
            try {
                boolean hasUserRole = false;
                Set<Role> userRoles = user.getRoles();
                for (Role role : userRoles) {
                    if (role.getName() == enumRole.ROLE_USER) {
                        hasUserRole = true;
                        break;
                    }
                }
                if (hasUserRole) {
                    DailyMenuReservation reservation = dailyMenuReservationService.createReservation(user, menu, starter);
                    return ResponseEntity.ok().body(reservation);
                } else {
                    return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
                }
            } catch (IllegalArgumentException e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }

        }
    }

    @GetMapping("/all")
    ResponseEntity<List<DailyMenuReservation>> all() {
        List<DailyMenuReservation> dailyMenuReservationList = dailyMenuReservationService.getAllDailyMenuReservation();
        return ResponseEntity.ok().body(dailyMenuReservationList);
    }

    @PreAuthorize("hasRole('STAFF')")
    @GetMapping("/{status}")
    ResponseEntity<List<DailyMenuReservation>> status(@PathVariable("status") ReservationStatus reservationStatus) {
        List<DailyMenuReservation> reservationStatusList = dailyMenuReservationService.getByStatus(reservationStatus);
        return ResponseEntity.ok().body(reservationStatusList);
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/user/{id}")
    ResponseEntity<List<DailyMenuReservation>> user(@PathVariable("id") Long id) {
        List<DailyMenuReservation> reservationStatusList = dailyMenuReservationService.getByUser(id);
        return ResponseEntity.ok().body(reservationStatusList);
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/user/today/{userId}")
    ResponseEntity<List<DailyMenuReservation>> getTodayReservationsForUser(@PathVariable("userId") Long id){
        List<DailyMenuReservation> todayList = dailyMenuReservationService.getByUserToday(id);
        return ResponseEntity.ok(todayList);
    }
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/user/history/{userId}")
    ResponseEntity<Map<LocalDate, List<DailyMenuReservation>>> getReservationsHistoryForUser(@PathVariable("userId") Long id){
        Map<LocalDate, List<DailyMenuReservation>> historyList = dailyMenuReservationService.getReservationsByUserId(id);
        if(historyList == null ){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(historyList);
    }

    /*work on this, fama erreur taa date converter fel postman check it */
    @GetMapping("/date/{id}")
    ResponseEntity<List<DailyMenuReservation>> userAndDate(@PathVariable("id") Long id, @RequestBody String localDateTime) {
        List<DailyMenuReservation> reservationStatusList = dailyMenuReservationService.getByUserAndDate(id, localDateConverter.date(localDateTime));
        return ResponseEntity.ok().body(reservationStatusList);
    }

    @PreAuthorize("hasRole('STAFF')")
    @GetMapping("/today")
    ResponseEntity<List<DailyMenuReservation>> todayDate() {
        List<DailyMenuReservation> reservationStatusList = dailyMenuReservationService.getByTodayDate();
        return ResponseEntity.ok().body(reservationStatusList);
    }

    @PreAuthorize("hasRole('STAFF')")
    @PutMapping("/treat/{idReservation}/{idStaff}")
    ResponseEntity<DailyMenuReservation> treatedReservation(@PathVariable("idReservation") Long reservation, @PathVariable("idStaff") Long staff) {
        DailyMenuReservation res = dailyMenuReservationService.treatReservation(reservation, staff);
        return ResponseEntity.ok().body(res);
    }

@PreAuthorize("hasRole('STAFF') ||" + " hasRole('USER')" )
    @PutMapping("/cancel/{idReservation}/{idStaff}")
    ResponseEntity<DailyMenuReservation> cancelReservation(@PathVariable("idReservation") Long reservation, @PathVariable("idStaff") Long staff) {
        DailyMenuReservation res = dailyMenuReservationService.cancelReservation(reservation, staff);
        return ResponseEntity.ok().body(res);
    }

    @PreAuthorize(" hasRole('USER')")
    @GetMapping("/filter/{id}/{status}")
    ResponseEntity<Map<LocalDate, List<DailyMenuReservation>>> filterByStatus(@PathVariable("id") Long idUser, @PathVariable String status) {
        Map<LocalDate, List<DailyMenuReservation>> res = dailyMenuReservationService.userFilterByStatus(idUser, status);
        return ResponseEntity.ok().body(res);
    }
}
