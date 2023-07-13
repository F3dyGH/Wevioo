package com.wevioo.cantine.services;

import java.util.List;

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

}
