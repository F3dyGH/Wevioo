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
    public Double getTodayMenuReservationsProfit() {
        Double profit = 0.0;
        if (statsService.calculateTotalDailyMenuProfit() != null){
            profit = statsService.calculateTotalDailyMenuProfit();
        }
        else {
            profit = 0.0;
        }
        return profit;    }

    @GetMapping("reservations/today/breakfast/profit")
    public Double getTodayBreakfastReservationsProfit() {
        Double profit = 0.0;
        if (statsService.calculateTotalDailyBreakfastProfit() != null){
            profit = statsService.calculateTotalDailyBreakfastProfit();
        }
        else {
            profit = 0.0;
        }
        return profit;
    }

    @GetMapping("reservations/today/drinks/profit")
    public Double getTodayDrinksReservationsProfit() {
        Double profit = 0.0;
        if (statsService.calculateTotalDailyDrinksProfit() != null){
            profit = statsService.calculateTotalDailyBreakfastProfit();
        }
        else {
            profit = 0.0;
        }
        return profit;
    }

    @GetMapping("reservations/today/profit")
    public Double getTodayReservationsProfit() {
        Double drinksProfit = statsService.calculateTotalDailyDrinksProfit();
        Double breakfastProfit = statsService.calculateTotalDailyBreakfastProfit();
        Double menuProfits = statsService.calculateTotalDailyMenuProfit();
        if (menuProfits == null) {
            menuProfits = 0.0;
        }
        if (breakfastProfit == null) {
            breakfastProfit = 0.0;
        }
        if (drinksProfit == null) {
            drinksProfit = 0.0;
        }
        Double all = menuProfits + breakfastProfit + drinksProfit;
        return all;
    }

    @GetMapping("reservations/yesterday/profit")
    public Double getYesterdayReservationsProfit() {
        Double drinksProfit = statsService.calculateTotalYesterdayDrinksProfit();
        Double breakfastProfit = statsService.calculateTotalYesterdayBreakfastProfit();
        Double menuProfits = statsService.calculateTotalYesterdayDrinksProfit();
        if (menuProfits == null) {
            menuProfits = 0.0;
        }
        if (breakfastProfit == null) {
            breakfastProfit = 0.0;
        }
        if (drinksProfit == null) {
            drinksProfit = 0.0;
        }
        Double all = menuProfits + breakfastProfit + drinksProfit;
        return all;
    }

    @GetMapping("reservations/monthly/profit")
    public List<Object[]> getMonthlyReservationsProfit() {
        return statsService.calculateMonthlyReservationsProfit();
    }

    @GetMapping("reservations/profit/percentage")
    public Double getProfitPercentage(){
        return statsService.calculateProfitPercentage();
    }
    @GetMapping("/a")
    public Double get(){
        return statsService.calculateTotalYesterdayDrinksProfit();
    }
}
