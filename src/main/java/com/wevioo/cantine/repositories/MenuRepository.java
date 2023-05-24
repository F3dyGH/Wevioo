package com.wevioo.cantine.repositories;

import com.wevioo.cantine.entities.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface MenuRepository extends JpaRepository<Menu, Long> {
    List<Menu> findByDate(LocalDate menuDate);

    Menu findByName(String name);

}
