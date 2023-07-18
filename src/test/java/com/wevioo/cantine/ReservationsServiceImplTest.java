package com.wevioo.cantine;

import com.wevioo.cantine.entities.*;
import com.wevioo.cantine.enums.ReservationStatus;
import com.wevioo.cantine.repositories.ReservationsRepository;
import com.wevioo.cantine.repositories.UserRepository;
import com.wevioo.cantine.services.INotificationService;
import com.wevioo.cantine.services.impl.ReservationsServiceImpl;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ReservationsServiceImplTest {
    @Mock
    private ReservationsRepository reservationsRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private INotificationService notificationService;

    @InjectMocks
    private ReservationsServiceImpl reservationsService;

    @Test
    public void testCreateReservation() {
        User user = new User();
        Menu menu = new Menu();
        menu.setDate(LocalDate.from(LocalDateTime.now()));
        Starter starter = new Starter();
        when(reservationsRepository.existsByUserAndDateBetweenAndStarterIdIsNotNullAndReservationStatusIn(
                any(User.class), any(LocalDateTime.class), any(LocalDateTime.class), any(ReservationStatus[].class)))
                .thenReturn(false);
        when(reservationsRepository.save(any(Reservations.class))).thenReturn(new Reservations());
        doNothing().when(notificationService).addNotificationToStaff(anyString());
        Reservations result = reservationsService.createReservation(user, menu, starter);
        assertNotNull(result);
    }

    @Test
    public void testCreateFoodAndDrinksReservation() {
        User user = new User();
        FoodAndDrinks foodAndDrinks = new FoodAndDrinks();
        when(reservationsRepository.save(any(Reservations.class))).thenReturn(new Reservations());
        Reservations result = reservationsService.createFoodAndDrinksReservation(user, foodAndDrinks);
        assertNotNull(result);
    }

    @Test
    public void testGetByStatus() {
        ReservationStatus reservationStatus = ReservationStatus.IN_PROCESS;
        List<Reservations> reservations = new ArrayList<>();
        when(reservationsRepository.findByReservationStatus(reservationStatus)).thenReturn(reservations);
        List<Reservations> result = reservationsService.getByStatus(reservationStatus);
        assertNotNull(result);
    }

    @Test
    public void testUserFilterByStatus() {
        Long id = 1L;
        String reservationStatus = "IN_PROCESS";
        List<Reservations> reservations = new ArrayList<>();
        when(reservationsRepository.findByUserIdAndReservationStatusOrderByDateDesc(id, ReservationStatus.valueOf(reservationStatus)))
                .thenReturn(reservations);
        Map<LocalDate, List<Reservations>> result = reservationsService.userFilterByStatus(id, reservationStatus);
        assertNotNull(result);
    }

    @Test
    public void testGetByUserAndDate() {
        Long id = 1L;
        LocalDateTime date = LocalDateTime.now();
        List<Reservations> reservations = new ArrayList<>();
        when(reservationsRepository.findByUserAndDate(id, date)).thenReturn(reservations);
        List<Reservations> result = reservationsService.getByUserAndDate(id, date);
        assertNotNull(result);
    }

    @Test
    public void testGetByTodayDate() {
        List<Reservations> reservations = new ArrayList<>();
        when(reservationsRepository.findByReservationStatusAndDateBetweenOrderByDateDesc(
                eq(ReservationStatus.IN_PROCESS), any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(reservations);
        List<Reservations> result = reservationsService.getByTodayDate();
        assertNotNull(result);
    }

    @Test
    public void testTreatReservation() {
        Long id = 1L;
        Long staffId = 2L;
        User staff = new User();
        Reservations reservation = new Reservations();
        when(userRepository.findById(staffId)).thenReturn(Optional.of(staff));
        when(reservationsRepository.findById(id)).thenReturn(Optional.of(reservation));
        when(reservationsRepository.save(any(Reservations.class))).thenReturn(reservation);
        Reservations result = reservationsService.treatReservation(id, staffId);
        assertNotNull(result);
    }

    @Test
    public void testCancelReservation() {
        Long id = 1L;
        Long userId = 2L;
        User user = new User();
        Reservations reservation = new Reservations();
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(reservationsRepository.findById(id)).thenReturn(Optional.of(reservation));
        when(reservationsRepository.save(any(Reservations.class))).thenReturn(reservation);
        Reservations result = reservationsService.cancelReservation(id, userId);
        assertNotNull(result);
    }

    @Test
    public void testGetByUserToday() {
        Long id = 1L;
        User user = new User();
        List<Reservations> reservations = new ArrayList<>();
        when(userRepository.findById(id)).thenReturn(Optional.of(user));
        when(reservationsRepository.findByUser(user)).thenReturn(reservations);
        List<Reservations> result = reservationsService.getByUserToday(id);
        assertNotNull(result);
    }

    @Test
    public void testGetReservationsByUserId() {
        Long userId = 1L;
        List<Reservations> reservations = new ArrayList<>();
        when(reservationsRepository.findByUserId(userId)).thenReturn(reservations);
        Map<LocalDate, List<Reservations>> result = reservationsService.getReservationsByUserId(userId);
        assertNotNull(result);
    }


}
