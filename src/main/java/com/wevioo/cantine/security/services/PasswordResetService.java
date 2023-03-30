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
    private PasswordResetTokenRepository passwordResetTokenRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

   /* public User findByEmail(String email) extends User {
        User user = userRepository.findByEmail(email);

        return userRepository.findByEmail(email);
    }*/

    public void createPasswordResetTokenForUser(User user, String token) {
        /*PasswordResetToken resetToken = new PasswordResetToken(token, user);
        passwordResetTokenRepository.save(resetToken);*/
        user.setResetToken(token);
        user.setResetTokenExpiration(LocalDateTime.now().plusHours(24));
        userRepository.save(user);
    }

    public Optional<User> getPasswordResetToken(String token) {
        return userRepository.findByResetToken(token);
    }

    public void changeUserPassword(User user, String newPassword) {
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }
}
