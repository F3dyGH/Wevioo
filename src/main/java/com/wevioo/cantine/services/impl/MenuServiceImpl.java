package com.wevioo.cantine.services.impl;

import com.wevioo.cantine.config.LocalDateConverter;
import com.wevioo.cantine.entities.Dessert;
import com.wevioo.cantine.entities.Menu;
import com.wevioo.cantine.repositories.DessertRepository;
import com.wevioo.cantine.repositories.MenuRepository;
import com.wevioo.cantine.services.IMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

@Service
public class MenuServiceImpl implements IMenuService {
    @Autowired
    MenuRepository menuRepository;
    @Autowired
    private DessertRepository dessertRepository;
    @Autowired
    private LocalDateConverter localDateConverter;

    @Override
    public Menu addMenu(Menu menu, MultipartFile file) throws IOException {
        if (file != null) {
            byte[] photoBytes = file.getBytes();
            menu.setImage(photoBytes);
        } else {
            menu.setImage(null);
        }
        String menuName = menu.getName();
        Dessert dessert = dessertRepository.findById(menu.getDessert().getId()).orElse(null);

        if (isMenuNameUnique(menuName)) {
            menu.setDessert(dessert);
            return menuRepository.save(menu);
        } else {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Menu exists already");
        }
    }

    @Override
    public Menu getMenuById(Long id) {
        return menuRepository.findById(id).orElse(null);
    }

    @Override
    public Menu updateMenu(Long id, Menu newMenu, MultipartFile file) throws IOException {
        Menu menu = menuRepository.findById(id).orElse(null);
        Dessert dessert = dessertRepository.findById(newMenu.getDessert().getId()).orElse(null);
        if (menu != null && dessert!= null) {
            menu.setName(newMenu.getName());
            menu.setDate(localDateConverter.convert(newMenu.getDate().toString()));
            menu.setPrice(newMenu.getPrice());
            menu.setDessert(dessert);
        }
        if (file != null) {
            byte[] photoBytes = file.getBytes();
            menu.setImage(photoBytes);
        } else if (file == null) {
            menu.setImage(menu.getImage());
        }
        return menuRepository.save(menu);
    }

    @Override
    public List<Menu> getAllMenus() {
        return menuRepository.findAll();
    }

    @Override
    public List<Menu> getAllMenusByDate(LocalDate date) {
        return menuRepository.findByDate(date);
    }

    @Override
    public Menu getMenuByName(String name) {
        return menuRepository.findByName(name);
    }

    @Override
    public void deleteMenu(Long idMenu) {
        Menu menu = menuRepository.findById(idMenu).orElse(null);
        menuRepository.delete(menu);
    }

    @Override
    public List<Menu> getMenusForTomorrow() {
        LocalDate tomorrow = LocalDate.now(ZoneId.of("Africa/Tunis")).plusDays(1);
        return menuRepository.findByDate(tomorrow);
    }

    public boolean isMenuNameUnique(String menuName) {
        List<Menu> existingMenus = menuRepository.findAll();

        for (Menu menu : existingMenus) {
            if (menu.getName().equals(menuName)) {
                return false;
            }
        }
        return true;
    }
}
