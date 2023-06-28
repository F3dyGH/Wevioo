package com.wevioo.cantine.repositories;

import com.wevioo.cantine.entities.Reservations;
import com.wevioo.cantine.entities.User;
import com.wevioo.cantine.enums.ReservationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ReservationsRepository extends JpaRepository<Reservations, Long> {

    @Query("SELECT r FROM Reservations r WHERE r.reservationStatus = :status ORDER BY r.date DESC ")
    List<Reservations> findByReservationStatus(@Param("status") ReservationStatus reservationStatus);

    List<Reservations> findByUser_IdAndReservationStatusOrderByDateDesc(Long id, ReservationStatus reservationStatus);



    List<Reservations> findByUser(User user);

    List<Reservations> findByUserAndDate(Long id, LocalDateTime date);

   /* @Query("SELECT r FROM Reservations r WHERE DATE(r.date) = CURRENT_DATE ORDER BY r.date DESC ")
    List<Reservations> findByTodayDate();*/
   @Query("SELECT r FROM Reservations r WHERE r.date >= :yesterdayHour AND r.date <= :todayMaxHour AND r.reservationStatus = 'IN_PROCESS' ORDER BY r.date DESC")
   List<Reservations> findReservationsBetweenYesterdayAndToday(LocalDateTime yesterdayHour, LocalDateTime todayMaxHour);


    boolean existsByUserAndReservationStatusAndDateBetween(User user, ReservationStatus reservationStatus, LocalDateTime dateStart, LocalDateTime dateEnd);


    List<Reservations> findByUserAndDateBetween(User user, LocalDateTime dateStart, LocalDateTime dateEnd);
    Reservations findOneByUserAndDateBetween(User user, LocalDateTime dateStart, LocalDateTime dateEnd);

    List<Reservations> findByUserAndMenuDateBetween(User user, LocalDate dateStart, LocalDate dateEnd);


    List<Reservations> findByUser_Id(Long id);


/*
    List<Reservations> findByDateBetween(LocalDateTime dateStart, LocalDateTime dateEnd);
*/

    boolean existsByUserAndDateBetween(User user, LocalDateTime startDate, LocalDateTime endDate);

}
