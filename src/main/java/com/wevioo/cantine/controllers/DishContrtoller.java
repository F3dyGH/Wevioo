package com.wevioo.cantine.controllers;

import com.wevioo.cantine.entities.Dish;
import com.wevioo.cantine.services.IDishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/staff/dish")
@PreAuthorize("hasRole('ROLE_STAFF')")
public class DishContrtoller {
    @Autowired
    IDishService iDishService;

    @PostMapping("/add")
    public Dish addDish(@RequestBody Dish dish){
        return iDishService.addDish(dish);
    }
    @PutMapping("/update") // hott l id as a PathVariable
    public Dish updateDish(@RequestBody Dish dish){
        return iDishService.updateDish(dish);
    }
    @GetMapping("/{id}")
    public Dish retreiveDessert(@PathVariable Long id){
        return iDishService.retreiveDish(id);
    }
    @GetMapping("/all")
    public List<Dish> retreiveAll(){
        return iDishService.retreiveAll();
    }
    @DeleteMapping("/delete/{id}")
    public void deleteDessert(@PathVariable Long id){
        iDishService.deleteDish(id);
    }
}
