package com.wevioo.cantine.services;

import com.wevioo.cantine.entities.User;

import java.util.List;

public interface IUserManagement {
    List<User> getAllUsers();

    User updateUserRole(Long idUser, Integer idRole);

    void deleteUser(Long idUser);
}
