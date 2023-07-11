package com.wevioo.cantine.controllers;

import com.wevioo.cantine.services.StatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/stats")
public class StatsController {

    @Autowired
    StatsService statsService;

    @GetMapping("/reservations/drinks/today")
    public Long countDrinksTodayReservations() {
        return statsService.getDrinksTodayReservationsCount();
    }

    @GetMapping("/reservations/drinks/today/cancelled")
    public Long countCancelledDrinksTodayReservations() {
        return statsService.getCancelledDrinksTodayReservationsCount();
    }

    @GetMapping("/reservations/drinks/today/pending")
    public Long countPendingDrinksTodayReservations() {
        return statsService.getPendingDrinksTodayReservationsCount();
    }

    @GetMapping("/reservations/drinks/today/all")
    public Long countAllDrinksTodayReservations() {
        return statsService.getAllDrinksTodayReservationsCount();
    }

    @GetMapping("/reservations/breakfast/today/cancelled")
    public Long countCancelledBreakfastTodayReservations() {
        return statsService.getCancelledBreakfastTodayReservationsCount();
    }

    @GetMapping("/reservations/breakfast/today/pending")
    public Long countPendingBreakfastTodayReservations() {
        return statsService.getPendingBreakfastTodayReservationsCount();
    }

    @GetMapping("/reservations/breakfast/today")
    public Long countBreakfastTodayReservations() {
        return statsService.getBreakfastTodayReservationsCount();
    }
    @GetMapping("/reservations/breakfast/today/all")
    public Long countAllBreakfastTodayReservations() {
        return statsService.getAllBreakfastTodayReservationsCount();
    }

    @GetMapping("/reservations/menu/today")
    public Long countMenuTodayReservations() {
        return statsService.getMenuTodayReservationsCount();
    }

    @GetMapping("/reservations/menu/today/all")
    public Long countAllMenuTodayReservations() {
        return statsService.getAllMenuTodayReservationsCount();
    }

    @GetMapping("/reservations/menu/today/cancelled")
    public Long countCancelledMenuTodayReservations() {
        return statsService.getCancelledMenuTodayReservationsCount();
    }

    @GetMapping("/reservations/menu/today/pending")
    public Long countPendingMenuTodayReservations() {
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

    @GetMapping("reservations/today/menu/profit")
    public Double getTodayMenuReservationsProfit(){
        return statsService.calculateTotalDailyMenuProfit();
    }

    @GetMapping("reservations/today/breakfast/profit")
    public Double getTodayBreakfastReservationsProfit(){
        return statsService.calculateTotalDailyBreakfastProfit();
    }

    @GetMapping("reservations/today/drinks/profit")
    public Double getTodayDrinksReservationsProfit(){
        return statsService.calculateTotalDailyDrinksProfit();
    }

    @GetMapping("reservations/today/profit")
    public Double getTodayReservationsProfit(){
        Double drinksProfit = statsService.calculateTotalDailyDrinksProfit();
        Double breakfastProfit = statsService.calculateTotalDailyBreakfastProfit();
        Double menuProfits = statsService.calculateTotalDailyMenuProfit();
        Double all = menuProfits + breakfastProfit + drinksProfit;
        return all;
    }

    @GetMapping("reservations/monthly/profit")
    public Map<String, Double> getMonthlyReservationsProfit(){
        return statsService.calculateMonthlyReservationsProfit();
    }
}
