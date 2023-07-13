package com.wevioo.cantine.services.impl;

import com.wevioo.cantine.entities.Dessert;
import com.wevioo.cantine.repositories.DessertRepository;
import com.wevioo.cantine.repositories.MenuRepository;
import com.wevioo.cantine.services.IDessertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class DessertServiceImpl implements IDessertService {
    @Autowired
    DessertRepository dessertRepository;
    @Autowired
    private MenuRepository menuRepository;

    @Override
    public Dessert createDessert(Dessert dessert, MultipartFile file) throws IOException {
        if (file != null) {
            byte[] photoBytes = file.getBytes();
            dessert.setImage(photoBytes);
        }else{
            dessert.setImage(null);
        }
        return dessertRepository.save(dessert);

    }

    @Override
    public Dessert updateDessert(Long id, Dessert newDessert, MultipartFile file) throws IOException {
        Dessert dessert = dessertRepository.findById(id).orElse(null);
        if (dessert != null) {
            dessert.setDescription(newDessert.getDescription());
            dessert.setName(newDessert.getName());
            dessert.setPrice(newDessert.getPrice());
            if (file != null) {
                byte[] photoBytes = file.getBytes();
                dessert.setImage(photoBytes);
            }
            return dessertRepository.save(dessert);
        } else {
            return (Dessert) ResponseEntity.notFound();
        }
    }

    @Override
    public List<Dessert> getAll() {
        return dessertRepository.findAll();
    }

    @Override
    public Dessert retreiveDessert(Long idDessert) {
        return dessertRepository.findById(idDessert).orElse(null);
    }

    @Transactional
    @Override
    public void deleteDessert(Long idDessert) {
        menuRepository.deleteByDessertId(idDessert);
        dessertRepository.deleteById(idDessert);
    }
}
