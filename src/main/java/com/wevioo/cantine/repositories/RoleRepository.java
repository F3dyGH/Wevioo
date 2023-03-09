package com.wevioo.cantine.repositories;

import com.wevioo.cantine.entities.Role;
import com.wevioo.cantine.enums.enumRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByName(enumRole name);

    @Override
    Optional<Role> findById(Integer id);
}
