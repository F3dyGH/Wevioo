package com.wevioo.cantine.controllers;

import com.wevioo.cantine.entities.Role;
import com.wevioo.cantine.services.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class RoleController {
    @Autowired
    IRoleService roleService;

    @GetMapping("/all-roles")
    public List<Role> getAll(){
        return roleService.getAllRoles();
    }
    @GetMapping("/role/{id}")
    public Role getRole(@PathVariable Integer id){
        return roleService.findRoleById(id);
    }
}
