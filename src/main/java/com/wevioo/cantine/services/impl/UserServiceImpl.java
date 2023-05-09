package com.wevioo.cantine.services.impl;

import com.wevioo.cantine.entities.Role;
import com.wevioo.cantine.entities.User;
import com.wevioo.cantine.repositories.UserRepository;
import com.wevioo.cantine.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Set;

@Service
public class UserServiceImpl implements IUserService {

    private static final String UPLOAD_DIR = "uploads/users_photos/";
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    UserRepository userRepository;

    @Override
    public void updatePassword(User user, String password) {
        user.setPassword(passwordEncoder.encode(password));
        user.setResetToken(null);
        user.setResetTokenExpiration(null);
        userRepository.save(user);
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id).get();
    }

    @Override
    public User updateUser(Long id, User newUser, MultipartFile file) throws IOException {
        User user = userRepository.findById(id).orElse(null);
        if (user != null) {
            user.setUsername(newUser.getUsername());
            user.setFirstname(newUser.getFirstname());
            user.setLastname(newUser.getLastname());
            if (file != null) {
                byte[] photoBytes = file.getBytes();
                user.setImage(photoBytes);
            }
            return userRepository.save(user);
        } else {
            return (User) ResponseEntity.notFound();
        }
    }

    @Override
    public User updateAdminPassword(Long id, String password) {
        User u = userRepository.findById(id).get();
        u.setPassword(passwordEncoder.encode(password));
        return userRepository.save(u);
    }


}
