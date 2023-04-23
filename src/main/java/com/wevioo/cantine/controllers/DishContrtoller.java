package com.wevioo.cantine.controllers;

import com.wevioo.cantine.entities.Dish;
import com.wevioo.cantine.entities.FileDB;
import com.wevioo.cantine.services.IDishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/staff/dish")
public class DishContrtoller {
    @Autowired
    IDishService iDishService;

    @PostMapping(value = "/add", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> addDish(@ModelAttribute Dish dish, @RequestParam(value = "photo", required = false) MultipartFile file) throws IOException {
        return ResponseEntity.ok(iDishService.createDish(dish, file));
    }
    @PutMapping(value ="/update/{id}")
    public Dish updateDish(@RequestBody Dish dish, @PathVariable Long id) {
        return iDishService.updateDish(id, dish);
    }

    @GetMapping("/{id}")
    public Dish retreiveDessert(@PathVariable Long id) {
        return iDishService.retreiveDish(id);
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAll() {
        return iDishService.getAll();
    }

    @DeleteMapping("/delete/{id}")
    public void deleteDessert(@PathVariable Long id) {
        iDishService.deleteDish(id);
    }
}
