package com.wevioo.cantine.services.impl;

import com.wevioo.cantine.entities.Role;
import com.wevioo.cantine.entities.User;
import com.wevioo.cantine.repositories.RoleRepository;
import com.wevioo.cantine.repositories.UserRepository;
import com.wevioo.cantine.services.IUserManagement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;


@Service
public class UserManagementImpl implements IUserManagement {
    @Autowired
    UserRepository ur;
    @Autowired
    RoleRepository rr;

    @Override
    public List<User> getAllEnabledUsers() {
        return ur.findEnabledUsers();
    }

    @Override
    public List<User> getAllDisabledUsers() {
        return ur.findDisabledUsers();
    }

    @Override
    public User updateUserRole(Long idUser, Integer idRole) {
        User user = ur.findById(idUser).get();
        Role role = rr.findById(idRole).get();
        Set<Role> roles = user.getRoles();
        roles.clear();
        roles.add(role);
        return ur.save(user);
    }

    @Override
    public void deleteUser(Long idUser) {
        ur.deleteById(idUser);
    }

    @Override
    public User getUserById(Long id) {
        return ur.findById(id).get();
    }

    @Override
    public void disableUser(Long userId) {
        User user = ur.findById(userId).get();
        if (user != null) {
            user.setIsEnabled(false);
            ur.save(user);
        } else {
            throw new IllegalArgumentException("User doesn't exist");
        }
    }

    @Override
    public void enableUser(Long userId) {
        User user = ur.findById(userId).get();
        if (user != null) {
            user.setIsEnabled(true);
            ur.save(user);
        } else {
            throw new IllegalArgumentException("User doesn't exist");
        }
    }
}
