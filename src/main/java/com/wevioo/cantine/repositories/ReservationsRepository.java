package com.wevioo.cantine.repositories;

import com.wevioo.cantine.entities.FoodAndDrinks;
import com.wevioo.cantine.entities.Reservations;
import com.wevioo.cantine.entities.User;
import com.wevioo.cantine.enums.Categories;
import com.wevioo.cantine.enums.ReservationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ReservationsRepository extends JpaRepository<Reservations, Long> {

    @Query("SELECT r FROM Reservations r WHERE r.reservationStatus = :status ORDER BY r.date DESC ")
    List<Reservations> findByReservationStatus(@Param("status") ReservationStatus reservationStatus);

    List<Reservations> findByUserIdAndReservationStatusOrderByDateDesc(Long id, ReservationStatus reservationStatus);

    List<Reservations> findByUser(User user);

    List<Reservations> findByUserAndDate(Long id, LocalDateTime date);

    List<Reservations> findByFood(FoodAndDrinks foodAndDrinks);

    @Query("SELECT r FROM Reservations r WHERE r.date >= :yesterdayHour AND r.date <= :todayMaxHour AND r.reservationStatus = 'IN_PROCESS' ORDER BY r.date DESC")
    List<Reservations> findReservationsBetweenYesterdayAndToday(LocalDateTime yesterdayHour, LocalDateTime todayMaxHour);

    @Modifying
    @Query("DELETE FROM Reservations r WHERE r.menu.id = :menuId")
    void deleteByMenuId(Long menuId);

    @Modifying
    @Query("DELETE FROM Reservations r WHERE r.starter.id = :starterId")
    void deleteByStarterId(Long starterId);


    boolean existsByUserAndReservationStatusAndDateBetween(User user, ReservationStatus reservationStatus, LocalDateTime dateStart, LocalDateTime dateEnd);

    List<Reservations> findByReservationStatusAndDateBetweenOrderByDateDesc(ReservationStatus reservationStatus, LocalDateTime dateStart, LocalDateTime dateEnd);

    List<Reservations> findByUserId(Long id);

    boolean existsByUserAndDateBetweenAndStarterIdIsNotNullAndReservationStatusIn(User user, LocalDateTime startDate, LocalDateTime endDate, ReservationStatus[] reservationStatuses);

    Long countByMenuIdNullAndFoodCategoriesAndReservationStatusAndDateBetween(Categories categories, ReservationStatus reservationStatus, LocalDateTime dateStart, LocalDateTime dateEnd);

    Long countByMenuIdNullAndFoodCategoriesAndDateBetween(Categories categories, LocalDateTime dateStart, LocalDateTime dateEnd);

    Long countByFoodIdNullAndReservationStatusAndDateBetween(ReservationStatus reservationStatus, LocalDateTime dateStart, LocalDateTime dateEnd);

    Long countByFoodIdNullAndDateBetween(LocalDateTime dateStart, LocalDateTime dateEnd);

    Long countByDateBetween(LocalDateTime dateStart, LocalDateTime dateEnd);

    @Query("SELECT food.name, COUNT(r) FROM Reservations r JOIN r.food food WHERE r.reservationStatus = 'TREATED' GROUP BY food.name")
    List<Object[]> getMealPopularity();

    @Query("SELECT CONCAT(YEAR(r.date), '-', MONTH(r.date)), COUNT(r) FROM Reservations r WHERE r.reservationStatus = 'TREATED' GROUP BY CONCAT(YEAR(r.date), '-', MONTH(r.date)) ORDER BY MIN(r.date)")
    List<Object[]> getMonthlyReservationCounts();

    @Query("SELECT CONCAT(YEAR(r.date), '-', MONTH(r.date)), SUM(r.menu.price) FROM Reservations r WHERE r.reservationStatus = 'TREATED' GROUP BY CONCAT(YEAR(r.date), '-', MONTH(r.date)) ORDER BY MIN(r.date)")
    List<Object[]> getMonthlyMenuReservationsProfit();

    @Query("SELECT CONCAT(YEAR(r.date), '-', MONTH(r.date)), SUM(r.food.price) FROM Reservations r WHERE r.reservationStatus = 'TREATED' AND r.food.categories = :category GROUP BY CONCAT(YEAR(r.date), '-', MONTH(r.date)) ORDER BY MIN(r.date)")
    List<Object[]> getMonthlyFoodReservationsProfit(@Param("category") Categories categories);

    @Query("SELECT SUM(r.menu.price) FROM Reservations r WHERE r.reservationStatus = 'TREATED' AND r.date BETWEEN :start AND :end")
    Double calculateTodayMenuReservationProfitBetween(@Param("start") LocalDateTime dateStart, @Param("end") LocalDateTime dateEnd);

    @Query("SELECT SUM(r.food.price) FROM Reservations r WHERE r.reservationStatus = 'TREATED' AND r.food.categories = :category AND r.date >= :dateStart AND r.date <= :dateEnd")
    Double calculateTodayFoodReservationProfitBetweenByCategories(@Param("category") Categories category, @Param("dateStart") LocalDateTime dateStart, @Param("dateEnd") LocalDateTime dateEnd);

}
