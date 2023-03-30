package com.wevioo.cantine.security.services;

import com.wevioo.cantine.entities.PasswordResetToken;
import com.wevioo.cantine.entities.User;
import com.wevioo.cantine.repositories.PasswordResetTokenRepository;
import com.wevioo.cantine.repositories.UserRepository;
import com.wevioo.cantine.security.payloads.response.MessageResponse;
import net.bytebuddy.asm.Advice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.Message;
import java.util.Locale;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordResetTokenRepository passwordResetToken;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("No User with " + username + "as a username"));

        return UserDetailsImpl.build(user);
    }

  /*  @Transactional
    public UserDetails loadUserByEmail(String email) throws UsernameNotFoundException{
        User user = userRepository.findByEmail(email);

        return UserDetailsImpl.build(user);
    }*/

    /*public void createPasswordResetToken(User user, String token){
        PasswordResetToken pwd = new PasswordResetToken(user,token);
        passwordResetToken.save(pwd);
    }
*/
}
