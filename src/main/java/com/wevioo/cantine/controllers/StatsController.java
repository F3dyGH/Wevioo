package com.wevioo.cantine.controllers;

import com.wevioo.cantine.services.StatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/stats")
public class StatsController {

    @Autowired
    StatsService statsService;

    @GetMapping("/reservations/drinks/today")
    public Long countDrinksTodayReservations(){
        return statsService.getDrinksTodayReservationsCount();
    }

    @GetMapping("/reservations/breakfast/today")
    public Long countBreakfastTodayReservations(){
        return statsService.getBreakfastTodayReservationsCount();
    }

    @GetMapping("/reservations/menu/today")
    public Long countMenuTodayReservations(){
        return statsService.getMenuTodayReservationsCount();
    }

    @GetMapping("/reservations/today")
    public Long countTodayReservations() {
        return statsService.getTodayReservationsCount();
    }

    @GetMapping("/popularity")
    public List<Object[]> getMealPopularity() {
        return statsService.getMealPopularity();
    }

    @GetMapping("/monthly")
    public List<Object[]> getReservationTrends() {
        return statsService.getReservationMonthly();
    }
}
