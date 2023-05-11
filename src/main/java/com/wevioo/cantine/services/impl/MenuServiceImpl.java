package com.wevioo.cantine.services.impl;

import com.wevioo.cantine.entities.Dish;
import com.wevioo.cantine.entities.Menu;
import com.wevioo.cantine.repositories.DishRepository;
import com.wevioo.cantine.repositories.MenuRepository;
import com.wevioo.cantine.services.IMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MenuServiceImpl implements IMenuService {
    @Autowired
    MenuRepository menuRepository;
    @Autowired
    private DishRepository dishRepository;

    @Override
    public ResponseEntity<?> addMenu(Menu menu) {
        List<Long> unavailableDishIds = new ArrayList<>();
        for (Dish dish : menu.getDishes()) {
            Optional<Dish> optionalDish = dishRepository.findById(dish.getId());
            if (!optionalDish.isPresent()) {
                unavailableDishIds.add(dish.getId());
            }
        }
        if (!unavailableDishIds.isEmpty()) {
            return ResponseEntity.badRequest().body("The following dishes are unavailable: " + unavailableDishIds.toString());
        }
        Menu savedMenu = menuRepository.save(menu);
        List<Long> savedDishIds = new ArrayList<>();
        for (Dish dish : savedMenu.getDishes()) {
            savedDishIds.add(dish.getId());
        }
        return ResponseEntity.ok().body("Menu created successfully with dishes: " + savedDishIds.toString() +" for the " + savedMenu.getDate());
    }

    @Override
    public Menu getMenuById(Long id) {
        return menuRepository.findById(id).get();
    }

    @Override
    public Menu updateMenu(Long id, Menu newMenu) {
        Menu menu = menuRepository.findById(id).orElse(null);
        if (menu != null) {
            menu.setDate(newMenu.getDate());
            menu.setDishes(newMenu.getDishes());
        }
        return menuRepository.save(menu);
    }

    @Override
    public ResponseEntity<?> getAllMenus() {
         List<Menu> menus = menuRepository.findAll();
         return ResponseEntity.ok().body(menus);
    }

    @Override
    public List<Menu> getAllMenusByDate(LocalDate date) {
        return menuRepository.findByDate(date);
    }

    @Override
    public void deleteMenu(Long idMenu) {

    }
}
