package com.wevioo.cantine.controllers;

import com.wevioo.cantine.entities.Dessert;
import com.wevioo.cantine.services.IDessertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/staff/dessert")
public class DessertController {
    @Autowired
    IDessertService iDessertService;

    @PostMapping(value = "/add", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> addDessert(@ModelAttribute Dessert dessert, @RequestParam(value = "photo", required = false) MultipartFile file) throws IOException {
        return ResponseEntity.ok(iDessertService.createDessert(dessert, file));
    }
    @PutMapping(value = "/update/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Dessert updateDessert(@PathVariable Long id,@ModelAttribute Dessert dessert, @RequestParam(value = "photo", required = false) MultipartFile file) throws IOException {
        return iDessertService.updateDessert(id, dessert, file);
    }
    @GetMapping("/{id}")
    public Dessert retreiveDessert(@PathVariable Long id){
        return iDessertService.retreiveDessert(id);
    }
    @GetMapping("/all")
    public List<Dessert> retreiveAll(){
        return iDessertService.getAll();
    }
    @DeleteMapping("/delete/{id}")
    public void deleteDessert(@PathVariable Long id){
        iDessertService.deleteDessert(id);
    }
}


