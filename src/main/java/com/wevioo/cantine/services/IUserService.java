package com.wevioo.cantine.services;

import com.wevioo.cantine.entities.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface IUserService {
    void updatePassword(User user, String password);
    User getUserById(Long id);

    User updateUser(Long id, User newUser, MultipartFile file) throws IOException;

    String uploadPhoto(Long id, MultipartFile file) throws IOException;

    ResponseEntity<byte[]> getPhoto(String photoName) throws IOException;
}
