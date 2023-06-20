package com.wevioo.cantine.repositories;

import com.wevioo.cantine.entities.DailyMenuReservation;
import com.wevioo.cantine.entities.User;
import com.wevioo.cantine.enums.ReservationStatus;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface DailyMenuReservationRepository extends JpaRepository<DailyMenuReservation, Long> {

    @Query("SELECT r FROM DailyMenuReservation r WHERE r.reservationStatus = :status ORDER BY r.date DESC ")
    List<DailyMenuReservation> findByReservationStatus(@Param("status") ReservationStatus reservationStatus);

    List<DailyMenuReservation> findByUser_IdAndReservationStatusOrderByDateDesc(Long id, ReservationStatus reservationStatus);



    List<DailyMenuReservation> findByUser(Long id);

    List<DailyMenuReservation> findByUserAndDate(Long id, LocalDateTime date);

    @Query("SELECT r FROM DailyMenuReservation r WHERE DATE(r.date) = CURRENT_DATE ORDER BY r.date DESC ")
    List<DailyMenuReservation> findByTodayDate();

    List<DailyMenuReservation> findByUserAndDate(User user, LocalDate today);

    List<DailyMenuReservation> findByUserAndDateBetween(User user, LocalDateTime dateStart, LocalDateTime dateEnd);

    List<DailyMenuReservation> findByUser_Id(Long id);


/*
    List<DailyMenuReservation> findByDateBetween(LocalDateTime dateStart, LocalDateTime dateEnd);
*/

    boolean existsByUserAndDateBetween(User user, LocalDateTime startDate, LocalDateTime endDate);

}
