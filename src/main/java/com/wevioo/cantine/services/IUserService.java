package com.wevioo.cantine.services;

import com.wevioo.cantine.entities.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface IUserService {
    void updatePassword(User user, String password);

    User getUserById(Long id);

    User updateUser(Long id, User newUser, MultipartFile file) throws IOException;

    User updateAdminPassword(Long id, String password);
}
