package com.wevioo.cantine.services;

import java.util.List;

public interface StatsService {

    Long getDrinksTodayReservationsCount();

    Long getBreakfastTodayReservationsCount();

    Long getMenuTodayReservationsCount();

    Long getTodayReservationsCount();

    List<Object[]> getMealPopularity();

    List<Object[]> getReservationMonthly();
}
