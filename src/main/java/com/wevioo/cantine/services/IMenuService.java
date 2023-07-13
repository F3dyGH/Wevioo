package com.wevioo.cantine.services;

import com.wevioo.cantine.entities.Menu;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public interface IMenuService {
    Menu addMenu(Menu menu, MultipartFile file) throws IOException;

    Menu getMenuById(Long id);

    Menu updateMenu(Long id, Menu newMenu, MultipartFile file) throws IOException;

    List<Menu> getAllMenus();

    List<Menu> getAllMenusByDate(LocalDate date);

    Menu getMenuByName(String name);

    @Transactional
    void deleteMenu(Long idMenu);

    List<Menu> getMenusForTomorrow();
}
