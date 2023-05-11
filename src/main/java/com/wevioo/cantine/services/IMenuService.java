package com.wevioo.cantine.services;

import com.wevioo.cantine.entities.Menu;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.List;

public interface IMenuService {
    ResponseEntity<?> addMenu(Menu menu);
    Menu getMenuById(Long id);
    Menu updateMenu(Long id ,Menu newMenu);
    ResponseEntity<?> getAllMenus();
    List<Menu> getAllMenusByDate(LocalDate date);
    void deleteMenu(Long idMenu);
}
