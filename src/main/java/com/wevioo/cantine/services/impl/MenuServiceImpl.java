package com.wevioo.cantine.services.impl;

import com.wevioo.cantine.entities.Dessert;
import com.wevioo.cantine.entities.Starter;
import com.wevioo.cantine.entities.Menu;
import com.wevioo.cantine.repositories.DessertRepository;
import com.wevioo.cantine.repositories.StarterRepository;
import com.wevioo.cantine.repositories.MenuRepository;
import com.wevioo.cantine.services.IMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MenuServiceImpl implements IMenuService {
    @Autowired
    MenuRepository menuRepository;
    @Autowired
    private StarterRepository starterRepository;
    @Autowired
    private DessertRepository dessertRepository;

    @Override
    public ResponseEntity<?> addMenu(Menu menu, MultipartFile file) throws IOException {
        List<Long> unavailableDishIds = new ArrayList<>();
        if (file != null) {
            byte[] photoBytes = file.getBytes();
            menu.setImage(photoBytes);
        } else {
            menu.setImage(null);
        }
        /*for (Starter starter : menu.getStarters()) {
            Optional<Starter> optionalDish = starterRepository.findById(starter.getId());
            if (!optionalDish.isPresent()) {
                unavailableDishIds.add(starter.getId());
            }
        }
        if (!unavailableDishIds.isEmpty()) {
            return ResponseEntity.badRequest().body("The following dishes are unavailable: " + unavailableDishIds.toString());
        }*/
        Optional<Dessert> optionalDessert = dessertRepository.findById(menu.getDessert().getId());
        if (!optionalDessert.isPresent()) {
            return ResponseEntity.badRequest().body("The following dessert is unavailable: " + optionalDessert.toString());
        } else {
            Menu savedMenu = menuRepository.save(menu);
            // List<Long> savedDishIds = new ArrayList<>();
        /*for (Starter starter : savedMenu.getStarters()) {
            savedDishIds.add(starter.getId());
        }*/
            return ResponseEntity.ok().body("Menu created successfully with dessert: " + optionalDessert.toString() + " for the " + savedMenu.getDate());
        }
    }

    @Override
    public Menu getMenuById(Long id) {
        return menuRepository.findById(id).get();
    }

    @Override
    public Menu updateMenu(Long id, Menu newMenu) {
        Menu menu = menuRepository.findById(id).orElse(null);
      /*  if (menu != null) {
            menu.setDate(newMenu.getDate());
            menu.setStarters(newMenu.getStarters());
        }*/
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
