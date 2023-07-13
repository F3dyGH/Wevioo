package com.wevioo.cantine.services;

import com.wevioo.cantine.entities.Dessert;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface IDessertService {
    Dessert createDessert(Dessert dessert, MultipartFile file) throws IOException;
    Dessert updateDessert(Long id, Dessert newDessert, MultipartFile file) throws IOException;
    List<Dessert> getAll();
    Dessert retreiveDessert(Long idDessert);
    void deleteDessert(Long idDessert);
}
