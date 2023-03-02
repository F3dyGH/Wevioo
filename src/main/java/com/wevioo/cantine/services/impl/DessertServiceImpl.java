package com.wevioo.cantine.services.impl;

import com.wevioo.cantine.entities.Dessert;
import com.wevioo.cantine.repositories.DessertRepository;
import com.wevioo.cantine.services.IDessertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DessertServiceImpl implements IDessertService {
    @Autowired
    DessertRepository dessertRepository;
    @Override
    public Dessert addDessert(Dessert dessert) {
        return dessertRepository.save(dessert);
    }

    @Override
    public Dessert updateDessert(Dessert newDessert) {
        Dessert dessert = dessertRepository.findById(newDessert.getId()).orElse(null);
        if (dessert != null){
            dessert.setDescription(newDessert.getDescription());
            dessert.setName(newDessert.getName());
            dessert.setPrice(newDessert.getPrice());
            dessert.setPhoto(newDessert.getPhoto());
            return dessertRepository.save(dessert);
        }else {
            return (Dessert) ResponseEntity.notFound();
        }
    }

    @Override
    public List<Dessert> retreiveAll() {
        return dessertRepository.findAll();
    }

    @Override
    public Dessert retreiveDessert(Long idDessert) {
        return dessertRepository.findById(idDessert).orElse(null);
    }

    @Override
    public void deleteDessert(Long idDessert) {
        dessertRepository.deleteById(idDessert);
    }
}
