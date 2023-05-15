package com.wevioo.cantine.repositories;

import com.wevioo.cantine.entities.Starter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StarterRepository extends JpaRepository<Starter, Long> {
    Starter findByName(String name);
}
