package com.wevioo.cantine.controllers;

import com.wevioo.cantine.entities.Menu;
import com.wevioo.cantine.repositories.MenuRepository;
import com.wevioo.cantine.services.IMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/staff/menu")
public class MenuController {

    @Autowired
    IMenuService menuService;

    @Autowired
    MenuRepository menuRepository;

    @PostMapping("/create")
    public ResponseEntity<?> createMenu(@RequestBody Menu menu){
        return menuService.addMenu(menu);
        //return ResponseEntity.ok().body("Menu created successfully\n"+ menu);
    }
    @GetMapping("/all")
    public ResponseEntity<?> getAllMenus(){

        return menuService.getAllMenus();
    }
}
