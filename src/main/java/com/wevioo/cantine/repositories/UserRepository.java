package com.wevioo.cantine.repositories;

import com.wevioo.cantine.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    Optional<User> findByResetToken(String resetToken);

    User findByPhoto(String photo);

    Boolean existsByUsername(String username);

    @Query("SELECT u FROM User u WHERE u.isEnabled = true")
    List<User> findEnabledUsers();

    @Query("SELECT u FROM User u WHERE u.isEnabled = false")
    List<User> findDisabledUsers();

}
