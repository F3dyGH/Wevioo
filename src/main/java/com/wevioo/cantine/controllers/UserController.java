package com.wevioo.cantine.controllers;

import com.wevioo.cantine.entities.FoodAndDrinks;
import com.wevioo.cantine.entities.Menu;
import com.wevioo.cantine.entities.User;
import com.wevioo.cantine.enums.Categories;
import com.wevioo.cantine.services.IFoodAndDrinksService;
import com.wevioo.cantine.services.IMenuService;
import com.wevioo.cantine.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    IUserService userService;

    @Autowired
    IMenuService menuService;

    @Autowired
    IFoodAndDrinksService foodAndDrinksService;

    @PreAuthorize("hasRole('USER')")
    @PutMapping(value ="/update/{id}",  consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public User updateUser(@ModelAttribute User user, @RequestParam(value = "file", required = false) MultipartFile file, @PathVariable Long id)  throws IOException{
        return userService.updateUser(id, user, file);
    }
    @PreAuthorize("hasRole('ADMIN')" + " || hasRole('USER')" + "|| hasRole('STAFF')")
    @PutMapping("/changePassword/{id}")
    public ResponseEntity<User> updateUser(@RequestParam String password, @PathVariable Long id) {
        userService.updateAdminPassword(id,password);
        return ResponseEntity.ok().build();
    }
    @PreAuthorize("hasRole('ADMIN')" + " || hasRole('USER')" + "|| hasRole('STAFF')")
    @GetMapping("/menus/{date}")
    public ResponseEntity<List<Menu>> getMenusByDate(@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date){
        List<Menu> menusByDate = menuService.getAllMenusByDate(date);
        if (menusByDate.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }else {
            return ResponseEntity.status(HttpStatus.OK).body(menusByDate);
        }
    }

    @PreAuthorize("hasRole('ADMIN')" + " || hasRole('USER')" + "|| hasRole('STAFF')")
    @GetMapping("/menus/daily")
    public ResponseEntity<List<Menu>> getDailyMenu(){
        LocalTime currentTime = LocalTime.now();

        if (currentTime.isBefore(LocalTime.of(18, 0))) {
            LocalDate today = LocalDate.now(ZoneId.of("Africa/Tunis"));
            List<Menu> menus = menuService.getAllMenusByDate(today);
            return ResponseEntity.ok(menus);
        } else {
            LocalDate tomorrow = LocalDate.now(ZoneId.of("Africa/Tunis")).plusDays(1);
            List<Menu> menus = menuService.getAllMenusByDate(tomorrow);
            return ResponseEntity.ok( menus);
        }
    }
    @PreAuthorize("hasRole('USER')" + "|| hasRole('STAFF')" + "|| hasRole('ADMIN')")
    @GetMapping("/menu/{name}")
    public ResponseEntity<Menu> getMenuByName(@PathVariable("name") String name){
        Menu menu = menuService.getMenuByName(name);
        if(menu!=null){
            return ResponseEntity.ok().body(menu);
        }
        else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
    @PreAuthorize("hasRole('USER')" + "|| hasRole('STAFF')" + "|| hasRole('ADMIN')")
    @GetMapping("/menu/tomorrow")
    public ResponseEntity<List<Menu>> getMenusForTomorrow() {
        List<Menu> menus = menuService.getMenusForTomorrow();
        return ResponseEntity.ok(menus);
    }
    @PreAuthorize("hasRole('USER')" + "|| hasRole('STAFF')" + "|| hasRole('ADMIN')")
    @GetMapping("/foodDrinks/{name}")
    public ResponseEntity<FoodAndDrinks> getByName(@PathVariable("name") String name){
        FoodAndDrinks foodAndDrinks = foodAndDrinksService.getByName(name);
        if(foodAndDrinks!=null){
            return ResponseEntity.ok().body(foodAndDrinks);
        }
        else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
    @PreAuthorize("hasRole('ADMIN')" + " || hasRole('USER')" + "|| hasRole('STAFF')")
    @GetMapping("/{category}")
    public ResponseEntity<List<FoodAndDrinks>> getByCategory(@PathVariable("category") Categories categories){
        List<FoodAndDrinks> category = foodAndDrinksService.getByCategory(categories);
        return ResponseEntity.status(HttpStatus.OK).body(category);
    }
}
