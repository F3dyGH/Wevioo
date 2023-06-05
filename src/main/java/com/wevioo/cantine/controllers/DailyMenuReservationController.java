package com.wevioo.cantine.controllers;

import com.wevioo.cantine.config.LocalDateConverter;
import com.wevioo.cantine.entities.DailyMenuReservation;
import com.wevioo.cantine.entities.Menu;
import com.wevioo.cantine.entities.User;
import com.wevioo.cantine.enums.ReservationStatus;
import com.wevioo.cantine.repositories.MenuRepository;
import com.wevioo.cantine.repositories.UserRepository;
import com.wevioo.cantine.services.IDailyMenuReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


import java.time.LocalTime;
import java.time.ZoneId;
import java.util.List;

@Controller
@RequestMapping("/menuReservation")
public class DailyMenuReservationController {

    @Autowired
    IDailyMenuReservationService dailyMenuReservationService;
    @Autowired
    MenuRepository menuRepository;
    @Autowired
    UserRepository userRepository;

    @Autowired
    LocalDateConverter localDateConverter;


    @PreAuthorize("hasRole('USER')")
    @PostMapping("/create")
    ResponseEntity<String> create(@RequestParam Long userId, @RequestParam Long menuId) {
        User user = userRepository.findById(userId).orElse(null);
        Menu menu = menuRepository.findById(menuId).orElse(null);
        LocalTime currentTime = LocalTime.now(ZoneId.of("Africa/Tunis"));
        if (currentTime.isBefore(LocalTime.of(18, 0)) || currentTime.isAfter(LocalTime.of(10, 0))) {
            return ResponseEntity.badRequest().body("Reservation creation is only allowed between 6 pm and 10 am of the next day.");
        } else {
            dailyMenuReservationService.createReservation(user, menu);
            return ResponseEntity.ok().body("Reservation Created Successfully");
        }
    }

    @GetMapping("/all")
    ResponseEntity<List<DailyMenuReservation>> all() {

        List<DailyMenuReservation> dailyMenuReservationList = dailyMenuReservationService.getAllDailyMenuReservation();
        return ResponseEntity.ok().body(dailyMenuReservationList);
    }

    @GetMapping("/{status}")
    ResponseEntity<List<DailyMenuReservation>> status(@PathVariable("status") ReservationStatus reservationStatus) {
        List<DailyMenuReservation> reservationStatusList = dailyMenuReservationService.getByStatus(reservationStatus);
        return ResponseEntity.ok().body(reservationStatusList);
    }

    @GetMapping("/user/{id}")
    ResponseEntity<List<DailyMenuReservation>> user(@PathVariable("id") Long id) {
        List<DailyMenuReservation> reservationStatusList = dailyMenuReservationService.getByUser(id);
        return ResponseEntity.ok().body(reservationStatusList);
    }

    /*work on this, fama erreur taa date converter fel postman check it */
    @GetMapping("/date/{id}")
    ResponseEntity<List<DailyMenuReservation>> userAndDate(@PathVariable("id") Long id, @RequestBody String localDateTime) {
        List<DailyMenuReservation> reservationStatusList = dailyMenuReservationService.getByUserAndDate(id, localDateConverter.date(localDateTime));
        System.out.println(localDateConverter.date(localDateTime));
        return ResponseEntity.ok().body(reservationStatusList);
    }

    @GetMapping("/today")
    ResponseEntity<List<DailyMenuReservation>> TodayDate() {
        List<DailyMenuReservation> reservationStatusList = dailyMenuReservationService.getByTodayDate();
        return ResponseEntity.ok().body(reservationStatusList);
    }
}
