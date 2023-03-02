package com.wevioo.cantine.services;

import com.wevioo.cantine.entities.Menu;

public interface IMenuService {
    Menu addMenu(Menu menu);
    Menu getMenu();
    Menu updateMenu(Menu newMenu);
    void deleteMenu(Long idMenu);
}
