package com.wevioo.cantine.services;

import com.wevioo.cantine.entities.Dish;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface IDishService {

    ResponseEntity<?> createDish(Dish dish, MultipartFile file) throws IOException /*throws IOException*/;

    Dish updateDish(Long id, Dish newDish, MultipartFile file) throws IOException;

    Dish retreiveDish(Long idDish);

    ResponseEntity<?> getAll();

    void deleteDish(Long idDish);
}
