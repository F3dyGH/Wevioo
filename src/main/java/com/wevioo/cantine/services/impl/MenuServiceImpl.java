package com.wevioo.cantine.services.impl;

import com.wevioo.cantine.config.LocalDateConverter;
import com.wevioo.cantine.entities.Dessert;
import com.wevioo.cantine.entities.Menu;
import com.wevioo.cantine.repositories.DessertRepository;
import com.wevioo.cantine.repositories.StarterRepository;
import com.wevioo.cantine.repositories.MenuRepository;
import com.wevioo.cantine.services.IMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@Service
public class MenuServiceImpl implements IMenuService {
    @Autowired
    MenuRepository menuRepository;
    @Autowired
    private StarterRepository starterRepository;
    @Autowired
    private DessertRepository dessertRepository;
    @Autowired
    private LocalDateConverter localDateConverter;

    @Override
    public Menu addMenu(Menu menu, MultipartFile file/*, Long idDessert*/) throws IOException {
        if (file != null) {
            byte[] photoBytes = file.getBytes();
            menu.setImage(photoBytes);
        } else {
            menu.setImage(null);
        }

        Dessert dessert = dessertRepository.findById(menu.getDessert().getId()).get();

        menu.setDessert(dessert);
        return menuRepository.save(menu);
    }

    @Override
    public Menu getMenuById(Long id) {
        return menuRepository.findById(id).get();
    }

    @Override
    public Menu updateMenu(Long id, Menu newMenu, MultipartFile file) throws IOException {
        Menu menu = menuRepository.findById(id).orElse(null);
        Dessert dessert = dessertRepository.findById(newMenu.getDessert().getId()).get();
        if (menu != null) {
            menu.setName(newMenu.getName());
            menu.setDate(localDateConverter.convert(newMenu.getDate().toString()));
            menu.setPrice(newMenu.getPrice());
            menu.setDessert(dessert);
        }
        if (file != null) {
            byte[] photoBytes = file.getBytes();
            menu.setImage(photoBytes);
        } else {
            menu.setImage(null);
        }
        return menuRepository.save(menu);
    }

    @Override
    public List<Menu> getAllMenus() {
         List<Menu> menus = menuRepository.findAll();
         return menus;
    }

    @Override
    public List<Menu> getAllMenusByDate(LocalDate date) {
        return menuRepository.findByDate(date);
    }

    @Override
    public void deleteMenu(Long idMenu) {
        Menu menu = menuRepository.findById(idMenu).get();
        menuRepository.delete(menu);
    }
}
