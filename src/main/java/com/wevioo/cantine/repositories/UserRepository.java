package com.wevioo.cantine.repositories;

import com.wevioo.cantine.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    Optional<User> findByResetToken(String resetToken);

//    User findByEmail(String email);


    Boolean existsByUsername(String username);

//    Boolean existsByEmail(String email);
}
