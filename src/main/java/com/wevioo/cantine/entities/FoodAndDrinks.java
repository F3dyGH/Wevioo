package com.wevioo.cantine.entities;

import com.wevioo.cantine.enums.Categories;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "food_and_drinks")
public class FoodAndDrinks {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "price")
    private Double price;

    @Column(name = "description")
    private String description;

    @Lob
    @Column(name = "image")
    private byte [] image;

    @Enumerated(EnumType.STRING)
    @Column(name = "categories")
    private Categories categories;

    @Column(name = "creation_date")
    private LocalDateTime creationDate;

    @Column(name = "modification_date")
    private LocalDateTime modificationDate;

}
