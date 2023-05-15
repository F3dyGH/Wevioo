package com.wevioo.cantine.controllers;

import com.wevioo.cantine.entities.Dish;
import com.wevioo.cantine.services.IDishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/staff/dish")
public class DishController {
    @Autowired
    IDishService iDishService;

    @PostMapping(value = "/add", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> addDish(@ModelAttribute Dish dish, @RequestParam(value = "photo", required = false) MultipartFile file) throws IOException {
        return ResponseEntity.ok(iDishService.createDish(dish, file));
    }
    @PutMapping(value ="/update/{id}",  consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Dish updateDish(@ModelAttribute Dish dish, @RequestParam(value = "photo", required = false) MultipartFile file, @PathVariable Long id)  throws IOException{
        return iDishService.updateDish(id, dish, file);
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
