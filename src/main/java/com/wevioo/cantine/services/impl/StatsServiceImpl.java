package com.wevioo.cantine.services.impl;

import com.wevioo.cantine.enums.Categories;
import com.wevioo.cantine.enums.ReservationStatus;
import com.wevioo.cantine.repositories.ReservationsRepository;
import com.wevioo.cantine.services.StatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Service
public class StatsServiceImpl implements StatsService {

    @Autowired
    ReservationsRepository reservationsRepository;

    @Override
    public Long getDrinksTodayReservationsCount() {
        LocalDateTime start = LocalDateTime.now(ZoneId.of("Africa/Tunis")).withHour(0).withMinute(0);
        LocalDateTime end = LocalDateTime.now(ZoneId.of("Africa/Tunis")).plusDays(1).withHour(0).withMinute(0);
        Categories category = Categories.drinks;
        ReservationStatus status = ReservationStatus.TREATED;
        return reservationsRepository.countByMenuIdNullAndFoodCategoriesAndReservationStatusAndDateBetween(category, status, start, end);
    }

    @Override
    public Long getBreakfastTodayReservationsCount() {
        LocalDateTime start = LocalDateTime.now(ZoneId.of("Africa/Tunis")).minusDays(1).withHour(18).withMinute(0);
        LocalDateTime end = LocalDateTime.now(ZoneId.of("Africa/Tunis")).withHour(18).withMinute(0).withSecond(0);
        Categories category = Categories.breakfast;
        ReservationStatus status = ReservationStatus.TREATED;
        return reservationsRepository.countByMenuIdNullAndFoodCategoriesAndReservationStatusAndDateBetween(category, status, start, end);
    }

    @Override
    public Long getMenuTodayReservationsCount() {
        LocalDateTime start = LocalDateTime.now(ZoneId.of("Africa/Tunis")).minusDays(1).withHour(18).withMinute(0);
        LocalDateTime end = LocalDateTime.now(ZoneId.of("Africa/Tunis")).withHour(10).withMinute(0);
        ReservationStatus status = ReservationStatus.TREATED;
        return reservationsRepository.countByFoodIdNullAndReservationStatusAndDateBetween(status, start, end);
    }

    @Override
    public Long getTodayReservationsCount() {
        LocalDateTime start = LocalDateTime.now(ZoneId.of("Africa/Tunis")).minusDays(1).withHour(18).withMinute(0);
        LocalDateTime end = LocalDateTime.now(ZoneId.of("Africa/Tunis")).withHour(10).withMinute(0);
        return reservationsRepository.countByDateBetween(start, end);
    }

    @Override
    public List<Object[]> getMealPopularity() {
        return reservationsRepository.getMealPopularity();
    }

    @Override
    public List<Object[]> getReservationMonthly() {
        return reservationsRepository.getMonthlyReservationCounts();
    }
}
