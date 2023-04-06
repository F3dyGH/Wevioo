package com.wevioo.cantine.services;

import com.wevioo.cantine.entities.User;

public interface IUserService {
    void updatePassword(User user, String password);
    User getUserById(Long id);
}
