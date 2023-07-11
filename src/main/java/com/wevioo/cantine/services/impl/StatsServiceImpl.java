package com.wevioo.cantine.services.impl;

import com.wevioo.cantine.enums.Categories;
import com.wevioo.cantine.enums.ReservationStatus;
import com.wevioo.cantine.repositories.ReservationsRepository;
import com.wevioo.cantine.services.StatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public Long getCancelledDrinksTodayReservationsCount() {
        LocalDateTime start = LocalDateTime.now(ZoneId.of("Africa/Tunis")).withHour(0).withMinute(0);
        LocalDateTime end = LocalDateTime.now(ZoneId.of("Africa/Tunis")).plusDays(1).withHour(0).withMinute(0);
        Categories category = Categories.drinks;
        ReservationStatus status = ReservationStatus.CANCELLED;
        return reservationsRepository.countByMenuIdNullAndFoodCategoriesAndReservationStatusAndDateBetween(category, status, start, end);
    }

    @Override
    public Long getPendingDrinksTodayReservationsCount() {
        LocalDateTime start = LocalDateTime.now(ZoneId.of("Africa/Tunis")).withHour(0).withMinute(0);
        LocalDateTime end = LocalDateTime.now(ZoneId.of("Africa/Tunis")).plusDays(1).withHour(0).withMinute(0);
        Categories category = Categories.drinks;
        ReservationStatus status = ReservationStatus.IN_PROCESS;
        return reservationsRepository.countByMenuIdNullAndFoodCategoriesAndReservationStatusAndDateBetween(category, status, start, end);
    }

    @Override
    public Long getAllDrinksTodayReservationsCount() {
        LocalDateTime start = LocalDateTime.now(ZoneId.of("Africa/Tunis")).withHour(0).withMinute(0);
        LocalDateTime end = LocalDateTime.now(ZoneId.of("Africa/Tunis")).plusDays(1).withHour(0).withMinute(0);
        Categories category = Categories.drinks;
        return reservationsRepository.countByMenuIdNullAndFoodCategoriesAndDateBetween(category, start, end);
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
    public Long getCancelledBreakfastTodayReservationsCount() {
        LocalDateTime start = LocalDateTime.now(ZoneId.of("Africa/Tunis")).minusDays(1).withHour(18).withMinute(0);
        LocalDateTime end = LocalDateTime.now(ZoneId.of("Africa/Tunis")).withHour(18).withMinute(0).withSecond(0);
        Categories category = Categories.breakfast;
        ReservationStatus status = ReservationStatus.CANCELLED;
        return reservationsRepository.countByMenuIdNullAndFoodCategoriesAndReservationStatusAndDateBetween(category, status, start, end);
    }

    @Override
    public Long getPendingBreakfastTodayReservationsCount() {
        LocalDateTime start = LocalDateTime.now(ZoneId.of("Africa/Tunis")).minusDays(1).withHour(18).withMinute(0);
        LocalDateTime end = LocalDateTime.now(ZoneId.of("Africa/Tunis")).withHour(18).withMinute(0).withSecond(0);
        Categories category = Categories.breakfast;
        ReservationStatus status = ReservationStatus.IN_PROCESS;
        return reservationsRepository.countByMenuIdNullAndFoodCategoriesAndReservationStatusAndDateBetween(category, status, start, end);
    }

    @Override
    public Long getAllBreakfastTodayReservationsCount() {
        LocalDateTime start = LocalDateTime.now(ZoneId.of("Africa/Tunis")).minusDays(1).withHour(18).withMinute(0);
        LocalDateTime end = LocalDateTime.now(ZoneId.of("Africa/Tunis")).withHour(18).withMinute(0).withSecond(0);
        Categories category = Categories.breakfast;
        return reservationsRepository.countByMenuIdNullAndFoodCategoriesAndDateBetween(category, start, end);
    }

    @Override
    public Long getMenuTodayReservationsCount() {
        LocalDateTime start = LocalDateTime.now(ZoneId.of("Africa/Tunis")).minusDays(1).withHour(18).withMinute(0);
        LocalDateTime end = LocalDateTime.now(ZoneId.of("Africa/Tunis")).withHour(10).withMinute(0);
        ReservationStatus status = ReservationStatus.TREATED;
        return reservationsRepository.countByFoodIdNullAndReservationStatusAndDateBetween(status, start, end);
    }

    @Override
    public Long getAllMenuTodayReservationsCount() {
        LocalDateTime start = LocalDateTime.now(ZoneId.of("Africa/Tunis")).minusDays(1).withHour(18).withMinute(0);
        LocalDateTime end = LocalDateTime.now(ZoneId.of("Africa/Tunis")).withHour(10).withMinute(0);
        return reservationsRepository.countByFoodIdNullAndDateBetween(start, end);
    }

    @Override
    public Long getCancelledMenuTodayReservationsCount() {
        LocalDateTime start = LocalDateTime.now(ZoneId.of("Africa/Tunis")).minusDays(1).withHour(18).withMinute(0);
        LocalDateTime end = LocalDateTime.now(ZoneId.of("Africa/Tunis")).withHour(10).withMinute(0);
        ReservationStatus status = ReservationStatus.CANCELLED;
        return reservationsRepository.countByFoodIdNullAndReservationStatusAndDateBetween(status, start, end);
    }

    @Override
    public Long getPendingMenuTodayReservationsCount() {
        LocalDateTime start = LocalDateTime.now(ZoneId.of("Africa/Tunis")).minusDays(1).withHour(18).withMinute(0);
        LocalDateTime end = LocalDateTime.now(ZoneId.of("Africa/Tunis")).withHour(10).withMinute(0);
        ReservationStatus status = ReservationStatus.IN_PROCESS;
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

    @Override
    public Double calculateTotalDailyMenuProfit() {
        LocalDateTime start = LocalDateTime.now(ZoneId.of("Africa/Tunis")).minusDays(1).withHour(18).withMinute(0);
        LocalDateTime end = LocalDateTime.now(ZoneId.of("Africa/Tunis")).withHour(18).withMinute(0);
        return reservationsRepository.calculateTodayMenuReservationProfitBetween(start, end);
    }

    @Override
    public Double calculateTotalDailyBreakfastProfit() {
        LocalDateTime start = LocalDateTime.now(ZoneId.of("Africa/Tunis")).minusDays(1).withHour(18).withMinute(0);
        LocalDateTime end = LocalDateTime.now(ZoneId.of("Africa/Tunis")).withHour(18).withMinute(0);
        return reservationsRepository.calculateTodayFoodReservationProfitBetweenByCategories(Categories.breakfast, start, end);
    }

    @Override
    public Double calculateTotalDailyDrinksProfit() {
        LocalDateTime start = LocalDateTime.now(ZoneId.of("Africa/Tunis")).minusDays(1).withHour(18).withMinute(0);
        LocalDateTime end = LocalDateTime.now(ZoneId.of("Africa/Tunis")).withHour(18).withMinute(0);
        return reservationsRepository.calculateTodayFoodReservationProfitBetweenByCategories(Categories.drinks, start, end);
    }

    @Override
    public Map<String, Double> calculateMonthlyReservationsProfit(){
        List<Object[]> menuProfits = reservationsRepository.getMonthlyMenuReservationsProfit();
        List<Object[]> breakfastProfits = reservationsRepository.getMonthlyFoodReservationsProfit(Categories.breakfast);
        List<Object[]> drinksProfit = reservationsRepository.getMonthlyFoodReservationsProfit(Categories.drinks);
        Map<String, Double> sumMap = new HashMap<>();
        for (Object[] menuProfit : menuProfits) {
            String date = (String) menuProfit[0];
            Double profit = (Double) menuProfit[1];
            sumMap.put(date, profit);
        }
        for (Object[] breakfastProfit : breakfastProfits) {
            String date = (String) breakfastProfit[0];
            Double profit = (Double) breakfastProfit[1];

            if (sumMap.containsKey(date)) {
                Double currentSum = sumMap.get(date);
                sumMap.put(date, currentSum + profit);
            } else {
                sumMap.put(date, profit);
            }
        }
        for (Object[] drinksProfits : drinksProfit) {
            String date = (String) drinksProfits[0];
            Double profit = (Double) drinksProfits[1];

            if (sumMap.containsKey(date)) {
                Double currentSum = sumMap.get(date);
                sumMap.put(date, currentSum + profit);
            } else {
                sumMap.put(date, profit);
            }
        }
        return sumMap;
    }
    @Override
    public List<Object[]> a(){
        List<Object[]> menuProfits = reservationsRepository.getMonthlyMenuReservationsProfit();
        List<Object[]> breakfastProfits = reservationsRepository.getMonthlyFoodReservationsProfit(Categories.breakfast);
        List<Object[]> drinksProfit = reservationsRepository.getMonthlyFoodReservationsProfit(Categories.drinks);
        return drinksProfit;
    }
}
