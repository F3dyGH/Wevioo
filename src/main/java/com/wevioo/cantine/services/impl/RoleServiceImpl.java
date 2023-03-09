package com.wevioo.cantine.services.impl;

import com.wevioo.cantine.entities.Role;
import com.wevioo.cantine.repositories.RoleRepository;
import com.wevioo.cantine.services.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements IRoleService {
    @Autowired
    RoleRepository roleRepository;
    @Override
    public Role findRoleById(Integer id) {
        return roleRepository.findById(id).get();
    }

    @Override
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }
}
