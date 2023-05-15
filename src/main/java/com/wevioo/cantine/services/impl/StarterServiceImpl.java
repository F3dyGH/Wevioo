package com.wevioo.cantine.services.impl;

import com.wevioo.cantine.entities.Dish;
import com.wevioo.cantine.repositories.DishRepository;
import com.wevioo.cantine.services.IDishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import java.io.IOException;

@Service
public class DishServiceImpl implements IDishService {

    @Autowired
    DishRepository dishRepository;
    @Autowired
    ServletContext servletContext;
    @Autowired
    ResourceLoader resourceLoader;

    @Override
    public ResponseEntity<?> createDish(Dish dish, MultipartFile file) throws IOException {
        if (file != null) {
            byte[] photoBytes = file.getBytes();
            dish.setImage(photoBytes);
        }else{
            dish.setImage(null);
        }
        dishRepository.save(dish);
        return ResponseEntity.ok("Dish created Successfully");
    }

    @Override
    public Dish updateDish(Long id, Dish newDish, MultipartFile file) throws IOException {
        Dish dish = dishRepository.findById(id).orElse(null);
        if (dish != null) {
            dish.setDescription(newDish.getDescription());
            dish.setName(newDish.getName());
            dish.setPrice(newDish.getPrice());
            if (file != null) {
                byte[] photoBytes = file.getBytes();
                dish.setImage(photoBytes);
            }
            return dishRepository.save(dish);
        } else {
            return (Dish) ResponseEntity.notFound();
        }
    }

    @Override
    public Dish retreiveDish(Long idDish) {
        return dishRepository.findById(idDish).orElse(null);
    }

    @Override
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok().body(dishRepository.findAll());
    }

    @Override
    public void deleteDish(Long idDish) {
        dishRepository.deleteById(idDish);
    }
}
