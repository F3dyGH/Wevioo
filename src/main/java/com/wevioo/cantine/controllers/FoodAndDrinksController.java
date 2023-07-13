package com.wevioo.cantine.controllers;

import com.wevioo.cantine.entities.FoodAndDrinks;
import com.wevioo.cantine.enums.Categories;
import com.wevioo.cantine.services.IFoodAndDrinksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/staff/foodDrinks")
public class FoodAndDrinksController {

    @Autowired
    IFoodAndDrinksService foodAndDrinksService;

    @PostMapping(value = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<FoodAndDrinks> create(@ModelAttribute FoodAndDrinks foodAndDrinks,
                                                @RequestParam(required = false) MultipartFile photo) throws IOException {
        FoodAndDrinks added = foodAndDrinksService.add(foodAndDrinks, photo);
        return ResponseEntity.status(HttpStatus.CREATED).body(added);
    }

    @PutMapping(value = "/update/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<FoodAndDrinks> update(@PathVariable Long id,
                                                @ModelAttribute FoodAndDrinks foodAndDrinks,
                                                @RequestParam(required = false) MultipartFile photo) throws IOException {
        FoodAndDrinks updated = foodAndDrinksService.update(id, foodAndDrinks, photo);
        return ResponseEntity.status(HttpStatus.OK).body(updated);
    }

    @GetMapping("/all")
    public ResponseEntity<List<FoodAndDrinks>> all(){
        List<FoodAndDrinks> getAll = foodAndDrinksService.getAll();
        return ResponseEntity.ok(getAll);
    }

    @GetMapping("/{category}")
     public ResponseEntity<List<FoodAndDrinks>> getByCategory(@PathVariable("category") Categories categories){
        List<FoodAndDrinks> category = foodAndDrinksService.getByCategory(categories);
        return ResponseEntity.status(HttpStatus.OK).body(category);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id){
        foodAndDrinksService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
