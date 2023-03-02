package com.wevioo.cantine.services;

import com.wevioo.cantine.entities.Dish;

import java.util.List;

public interface IDishService {
    Dish addDish(Dish dish);
    Dish updateDish(Dish newDish);
    Dish retreiveDish(Long idDish);
    List<Dish> retreiveAll();
    void deleteDish(Long idDish);
}
