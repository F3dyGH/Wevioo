package com.wevioo.cantine.services;

import com.wevioo.cantine.entities.Menu;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public interface IMenuService {
    ResponseEntity<?> addMenu(Menu menu, MultipartFile file) throws IOException;
    Menu getMenuById(Long id);
    Menu updateMenu(Long id ,Menu newMenu);
    ResponseEntity<?> getAllMenus();
    List<Menu> getAllMenusByDate(LocalDate date);
    void deleteMenu(Long idMenu);
}
