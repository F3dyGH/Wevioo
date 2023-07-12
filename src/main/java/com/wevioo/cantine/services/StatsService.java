package com.wevioo.cantine.services;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface StatsService {

    Long getDrinksTodayReservationsCount();

    Long getCancelledDrinksTodayReservationsCount();

    Long getPendingDrinksTodayReservationsCount();

    Long getAllDrinksTodayReservationsCount();

    Long getBreakfastTodayReservationsCount();

    Long getCancelledBreakfastTodayReservationsCount();

    Long getPendingBreakfastTodayReservationsCount();

    Long getAllBreakfastTodayReservationsCount();

    Long getMenuTodayReservationsCount();

    Long getAllMenuTodayReservationsCount();

    Long getCancelledMenuTodayReservationsCount();

    Long getPendingMenuTodayReservationsCount();

    Long getTodayReservationsCount();

    List<Object[]> getMealPopularity();

    List<Object[]> getReservationMonthly();

    Double calculateTotalDailyMenuProfit();

    Double calculateTotalYesterdayMenuProfit();

    Double calculateTotalDailyBreakfastProfit();

    Double calculateTotalYesterdayBreakfastProfit();

    Double calculateTotalDailyDrinksProfit();

    Double calculateTotalYesterdayDrinksProfit();

    List<Object[]> calculateMonthlyReservationsProfit();

    Double calculateProfitPercentage();

    List<Object[]> a();
}
