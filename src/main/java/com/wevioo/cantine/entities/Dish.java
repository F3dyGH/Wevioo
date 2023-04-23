package com.wevioo.cantine.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "dish")
public class Dish {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "price")
    private double price;

  /*  @Column(name = "photo")
    private String photo;
*/
    @Column(name = "description")
    private String description;

    @Lob
    @Column(name = "image")
    private byte[] image;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "menu_id")
    private Menu menu;

   /* @JsonIgnore
    @ManyToMany
    @JoinTable(name = "dish_images",
            joinColumns = @JoinColumn(name = "dish_id"),
            inverseJoinColumns = @JoinColumn(name = "images_id"))
    private Set<FileDB> images = new LinkedHashSet<>();*/

  /*  @JsonIgnore
    @OneToMany(mappedBy = "dish")
    private Set<DishImage> dishImages = new LinkedHashSet<>();*/

}
