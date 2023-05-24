package com.wevioo.cantine.controllers;

import com.wevioo.cantine.config.LocalDateConverter;
import com.wevioo.cantine.entities.Menu;
import com.wevioo.cantine.repositories.MenuRepository;
import com.wevioo.cantine.services.IMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/staff/menu")
public class MenuController {

    @Autowired
    IMenuService menuService;

    @Autowired
    MenuRepository menuRepository;

    @Autowired
    private LocalDateConverter localDateConverter;

    @PostMapping(value = "/create" , consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Menu> createMenu(@ModelAttribute Menu menu,
                                           @RequestParam(value = "photo", required = false) MultipartFile imageFile) throws IOException {
        menu.setDate(localDateConverter.convert(menu.getDate().toString()));
        Menu createdMenu = menuService.addMenu(menu, imageFile);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdMenu);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Menu>> getAllMenus(){

       List<Menu> menus =  menuService.getAllMenus();
        return ResponseEntity.status(HttpStatus.OK).body(menus);
    }

    @PutMapping(value = "/update/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Menu> updateMenu(@PathVariable Long id, @ModelAttribute Menu menu, @RequestParam(value = "photo", required = false) MultipartFile file) throws IOException {
        Menu updatedMenu = menuService.updateMenu(id,menu,file);
        return ResponseEntity.ok().body(updatedMenu);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteMenu(@PathVariable Long id){
        menuService.deleteMenu(id);
        return ResponseEntity.ok().build();
    }

}
