package com.wevioo.cantine.services.impl;

import com.wevioo.cantine.entities.Role;
import com.wevioo.cantine.entities.User;
import com.wevioo.cantine.repositories.RoleRepository;
import com.wevioo.cantine.repositories.UserRepository;
import com.wevioo.cantine.services.IUserManagement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
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
        Optional<User> optionalUser = ur.findById(idUser);
        Optional<Role> optionalRole = rr.findById(idRole);

        if (optionalUser.isPresent() && optionalRole.isPresent()) {
            User user = optionalUser.get();
            Role role = optionalRole.get();

            Set<Role> roles = user.getRoles();
            roles.clear();
            roles.add(role);

            return ur.save(user);
        } else {
            throw new IllegalArgumentException("User or role not found");
        }
    }

    @Override
    public void deleteUser(Long idUser) {
        ur.deleteById(idUser);
    }

    @Override
    public User getUserById(Long id) {
        Optional<User> optionalUser = ur.findById(id);

        if (optionalUser.isPresent()) {
            return optionalUser.get();
        } else {
            throw new IllegalArgumentException("User not found");
        }
    }

    @Override
    public void disableUser(Long userId) {
        Optional<User> optionalUser = ur.findById(userId);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setIsEnabled(false);
            ur.save(user);
        } else {
            throw new IllegalArgumentException("User doesn't exist");
        }
    }

    @Override
    public void enableUser(Long userId) {
        Optional<User> optionalUser = ur.findById(userId);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setIsEnabled(true);
            ur.save(user);
        } else {
            throw new IllegalArgumentException("User not found");
        }
    }
}
