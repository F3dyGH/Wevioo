package com.wevioo.cantine.services;

import com.wevioo.cantine.entities.Dish;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface IDishService {
    Dish addDish(Dish dish, MultipartFile file) throws IOException;

    void uploadPhoto(Long id, MultipartFile file) throws IOException;

    ResponseEntity<byte[]> getPhoto(String photoName) throws IOException;

    Dish updateDish(Long id, Dish newDish);

//    Dish updatePhoto(Long idDish, String idPhoto);

    Dish retreiveDish(Long idDish);
    List<Dish> retreiveAll();
    void deleteDish(Long idDish);
}
