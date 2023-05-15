package com.wevioo.cantine.controllers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wevioo.cantine.config.LocalDateConverter;
import com.wevioo.cantine.entities.Menu;
import com.wevioo.cantine.entities.Starter;
import com.wevioo.cantine.repositories.MenuRepository;
import com.wevioo.cantine.services.IMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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
    public ResponseEntity<?> createMenu(@ModelAttribute Menu menu,  @RequestParam(value = "photo", required = false) MultipartFile file) throws IOException {
        menu.setDate(localDateConverter.convert(menu.getDate().toString()));

        return menuService.addMenu(menu, file);
        //return ResponseEntity.ok().body("Menu created successfully\n"+ menu);
    }
    @GetMapping("/all")
    public ResponseEntity<?> getAllMenus(){

        return menuService.getAllMenus();
    }
}
