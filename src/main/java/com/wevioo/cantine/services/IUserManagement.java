package com.wevioo.cantine.services;

import com.wevioo.cantine.entities.User;

import java.util.List;

public interface IUserManagement {
    List<User> getAllEnabledUsers();

    List<User> getAllDisabledUsers();

    User updateUserRole(Long idUser, Integer idRole);

    void deleteUser(Long idUser);

    User getUserById(Long id);

    void disableUser(Long userId);
    void enableUser(Long userId);
}
