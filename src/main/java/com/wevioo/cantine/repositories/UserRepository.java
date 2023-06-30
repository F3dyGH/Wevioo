package com.wevioo.cantine.repositories;

import com.wevioo.cantine.entities.User;
import com.wevioo.cantine.enums.enumRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findByRolesName(String name);
    Optional<User> findByUsername(String username);

    Optional<User> findByResetToken(String resetToken);

    Boolean existsByUsername(String username);

    @Query("SELECT u FROM User u WHERE u.isEnabled = true")
    List<User> findEnabledUsers();

    @Query("SELECT u FROM User u WHERE u.isEnabled = false")
    List<User> findDisabledUsers();

}
