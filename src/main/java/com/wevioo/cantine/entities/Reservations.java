package com.wevioo.cantine.entities;

import com.wevioo.cantine.enums.ReservationStatus;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "reservations")
public class Reservations {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "menu_id")
    private Menu menu;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "date")
    private LocalDateTime date;

    @Enumerated(EnumType.STRING)
    @Column(name = "reservation_status")
    private ReservationStatus reservationStatus;

    @ManyToOne
    @JoinColumn(name = "staff_id")
    private User staff;

    @OneToOne
    @JoinColumn(name = "starter_id")
    private Starter starter;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "food_and_drinks_id")
    private FoodAndDrinks food;
}
