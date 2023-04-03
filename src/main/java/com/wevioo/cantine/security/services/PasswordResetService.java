package com.wevioo.cantine.security.services;

import com.wevioo.cantine.entities.User;
import com.wevioo.cantine.repositories.PasswordResetTokenRepository;
import com.wevioo.cantine.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class PasswordResetService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;


    public void createPasswordResetTokenForUser(User user, String token) {
        LocalDateTime linkExpiration = LocalDateTime.now().plusHours(1);
        user.setResetToken(token);
        user.setResetTokenExpiration(linkExpiration);
        userRepository.save(user);
    }

    public Optional<User> getPasswordResetToken(String token) {
        return userRepository.findByResetToken(token);
    }

}
