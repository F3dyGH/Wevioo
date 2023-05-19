package com.wevioo.cantine.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "menu")
public class Menu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "price")
    private Double price;

    @Lob
    @Column(name = "image")
    private byte[] image;

    @OneToOne
    @JoinColumn(name = "dessert_id")
    private Dessert dessert;



/*  @ManyToMany
    @JoinTable(name = "menu_dishes",
            joinColumns = @JoinColumn(name = "menu_id"),
            inverseJoinColumns = @JoinColumn(name = "dishes_id"))
    private List<Starter> starters = new ArrayList<>();*/


}

