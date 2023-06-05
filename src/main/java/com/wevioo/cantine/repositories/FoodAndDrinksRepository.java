package com.wevioo.cantine.repositories;

import com.wevioo.cantine.entities.FoodAndDrinks;
import com.wevioo.cantine.enums.Categories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FoodAndDrinksRepository extends JpaRepository<FoodAndDrinks, Long> {

    @Query("SELECT f FROM FoodAndDrinks f WHERE f.categories = :categories ")
    List<FoodAndDrinks> findByCategory(@Param("categories") Categories categories);

    FoodAndDrinks findByName(String name);




}
