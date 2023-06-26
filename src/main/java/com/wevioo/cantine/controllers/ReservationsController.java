package com.wevioo.cantine.controllers;

import com.wevioo.cantine.config.LocalDateConverter;
import com.wevioo.cantine.entities.*;
import com.wevioo.cantine.enums.ReservationStatus;
import com.wevioo.cantine.enums.enumRole;
import com.wevioo.cantine.repositories.FoodAndDrinksRepository;
import com.wevioo.cantine.repositories.MenuRepository;
import com.wevioo.cantine.repositories.StarterRepository;
import com.wevioo.cantine.repositories.UserRepository;
import com.wevioo.cantine.services.ReservationsService;
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
import java.util.Optional;
import java.util.Set;

@Controller
@RequestMapping("/menuReservation")
public class ReservationsController {

    @Autowired
    ReservationsService dailyMenuReservationService;
    @Autowired
    MenuRepository menuRepository;

    @Autowired
    StarterRepository starterRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    LocalDateConverter localDateConverter;
    @Autowired
    private FoodAndDrinksRepository foodAndDrinksRepository;


    @PreAuthorize("hasRole('USER')")
    @PostMapping("/create")
    ResponseEntity<Reservations> create(@RequestParam Long userId, @RequestParam Long menuId, @RequestParam Long starterId) {
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
                    Reservations reservation = dailyMenuReservationService.createReservation(user, menu, starter);
                    return ResponseEntity.ok().body(reservation);
                } else {
                    return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
                }
            } catch (IllegalArgumentException e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
        }
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/add")
    ResponseEntity add(@RequestParam Long userId, @RequestParam Long itemId) {
        User user = userRepository.findById(userId).orElse(null);
        Optional<FoodAndDrinks> foodAndDrinksOptional = foodAndDrinksRepository.findById(itemId);

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
                if (foodAndDrinksOptional.isPresent()) {
                    FoodAndDrinks foodAndDrinks = foodAndDrinksOptional.get();
                    int remainingQuantity = foodAndDrinks.getQuantity();
                    if (remainingQuantity > 0) {
                        foodAndDrinks.setQuantity(remainingQuantity - 1);
                        foodAndDrinksRepository.save(foodAndDrinks);
                    } else {
                        return ResponseEntity.badRequest().build();
                    }
                    Reservations reservation = dailyMenuReservationService.createFoodAndDrinksReservation(user, foodAndDrinks);
                    return ResponseEntity.ok().body(reservation);
                } else {
                    return ResponseEntity.notFound().build();
                }
            } else {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PreAuthorize("hasRole('STAFF')")
    @GetMapping("/{status}")
    ResponseEntity<List<Reservations>> status(@PathVariable("status") ReservationStatus reservationStatus) {
        List<Reservations> reservationStatusList = dailyMenuReservationService.getByStatus(reservationStatus);
        return ResponseEntity.ok().body(reservationStatusList);
    }

    /*@PreAuthorize("hasRole('USER')")
    @GetMapping("/user/{id}")
    ResponseEntity<List<Reservations>> user(@PathVariable("id") Long id) {
        List<Reservations> reservationList = dailyMenuReservationService.getByUser(id);
        return ResponseEntity.ok().body(reservationList);
    }*/

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/user/today/{userId}")
    ResponseEntity<List<Reservations>> getTodayReservationsForUser(@PathVariable("userId") Long id) {
        List<Reservations> todayList = dailyMenuReservationService.getByUserToday(id);
        return ResponseEntity.ok(todayList);
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/user/history/{userId}")
    ResponseEntity<Map<LocalDate, List<Reservations>>> getReservationsHistoryForUser(@PathVariable("userId") Long id) {
        Map<LocalDate, List<Reservations>> historyList = dailyMenuReservationService.getReservationsByUserId(id);
        if (historyList == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(historyList);
    }

    @PreAuthorize("hasRole('STAFF')")
    @GetMapping("/today")
    ResponseEntity<List<Reservations>> todayDate() {
        List<Reservations> reservationStatusList = dailyMenuReservationService.getByTodayDate();
        return ResponseEntity.ok().body(reservationStatusList);
    }

    @PreAuthorize("hasRole('STAFF')")
    @PutMapping("/treat/{idReservation}/{idStaff}")
    ResponseEntity<Reservations> treatedReservation(@PathVariable("idReservation") Long reservation, @PathVariable("idStaff") Long staff) {
        Reservations res = dailyMenuReservationService.treatReservation(reservation, staff);
        return ResponseEntity.ok().body(res);
    }

    @PreAuthorize("hasRole('STAFF') ||" + " hasRole('USER')")
    @PutMapping("/cancel/{idReservation}/{idStaff}")
    ResponseEntity<Reservations> cancelReservation(@PathVariable("idReservation") Long reservation, @PathVariable("idStaff") Long staff) {
        Reservations res = dailyMenuReservationService.cancelReservation(reservation, staff);
        return ResponseEntity.ok().body(res);
    }

    @PreAuthorize(" hasRole('USER')")
    @GetMapping("/filter/{id}/{status}")
    ResponseEntity<Map<LocalDate, List<Reservations>>> filterByStatus(@PathVariable("id") Long idUser, @PathVariable String status) {
        Map<LocalDate, List<Reservations>> res = dailyMenuReservationService.userFilterByStatus(idUser, status);
        return ResponseEntity.ok().body(res);
    }
}
