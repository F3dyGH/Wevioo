package com.wevioo.cantine.services.impl;

import com.wevioo.cantine.entities.Dish;
import com.wevioo.cantine.repositories.DishRepository;
import com.wevioo.cantine.services.IDishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DishServiceImpl implements IDishService {
    @Autowired
    DishRepository dishRepository;
    @Override
    public Dish addDish(Dish dish) {
        return dishRepository.save(dish);
    }

    @Override
    public Dish updateDish(Dish newDish) {
        Dish dish = dishRepository.findById(newDish.getId()).orElse(null);
        if (dish != null){
            dish.setDescription(newDish.getDescription());
            dish.setName(newDish.getName());
            dish.setPrice(newDish.getPrice());
            dish.setPhoto(newDish.getPhoto());
            dish.setMenu(newDish.getMenu());
            return dishRepository.save(dish);
        }else{
            return (Dish) ResponseEntity.notFound();
        }
    }

    @Override
    public Dish retreiveDish(Long idDish) {
        return dishRepository.findById(idDish).orElse(null);
    }

    @Override
    public List<Dish> retreiveAll() {
        return dishRepository.findAll();
    }

    @Override
    public void deleteDish(Long idDish) {
        dishRepository.deleteById(idDish);
    }
}
