package com.wevioo.cantine.repositories;

import com.wevioo.cantine.entities.FoodAndDrinks;
import com.wevioo.cantine.entities.Reservations;
import com.wevioo.cantine.entities.User;
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

   /* @Modifying
    @Query("DELETE FROM Reservations r WHERE r.foodAndDrinks.id = :foodId")
    void deleteByFoodId(Long foodId);*/

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
}
