package com.wevioo.cantine.controllers;

import com.wevioo.cantine.entities.Dessert;
import com.wevioo.cantine.services.IDessertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/staff/dessert")
//@PreAuthorize("hasRole('ROLE_STAFF')")
public class DessertController {
    @Autowired
    IDessertService iDessertService;

    @PostMapping("/add")
    public Dessert addDessert(@RequestBody Dessert dessert){
        return iDessertService.addDessert(dessert);
    }
    @PutMapping("/update")
    public Dessert updateDessert(@RequestBody Dessert dessert){
        return iDessertService.updateDessert(dessert);
    }
    @GetMapping("/{id}")
    public Dessert retreiveDessert(@PathVariable Long id){
        return iDessertService.retreiveDessert(id);
    }
    @GetMapping("/all")
    public List<Dessert> retreiveAll(){
        return iDessertService.retreiveAll();
    }
    @DeleteMapping("/delete/{id}")
    public void deleteDessert(@PathVariable Long id){
        iDessertService.deleteDessert(id);
    }
}


