package com.wevioo.cantine.services;

import com.wevioo.cantine.entities.User;

import java.util.List;

public interface IUserManagement {
    public List<User> getAllUsers();
    public User updateUserRole(Long idUser, User newUser);
    public void deleteUser(Long idUser);
}
