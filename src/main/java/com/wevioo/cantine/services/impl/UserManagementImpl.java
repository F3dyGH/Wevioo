package com.wevioo.cantine.services.impl;

import com.wevioo.cantine.entities.User;
import com.wevioo.cantine.repositories.UserRepository;
import com.wevioo.cantine.services.IUserManagement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UserManagementImpl implements IUserManagement {
    @Autowired
    UserRepository ur;
    @Override
    public List<User> getAllUsers() {
        return ur.findAll();
    }

    @Override
    public User updateUserRole(Long idUser, User newUser) {
        User user = ur.findById(idUser).get();
        if (user != null){
            user.setRoles(newUser.getRoles());
        }else {
            throw new UsernameNotFoundException("User doesnt exist");
        }
        return ur.save(user);
    }

    @Override
    public void deleteUser(Long idUser) {
        ur.deleteById(idUser);
    }
}
