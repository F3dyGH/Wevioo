package com.wevioo.cantine.services;

import com.wevioo.cantine.entities.Dessert;

import java.util.List;

public interface IDessertService {
    Dessert addDessert(Dessert dessert);
    Dessert updateDessert(Dessert newDessert);
    List<Dessert> retreiveAll();
    Dessert retreiveDessert(Long idDessert);
    void deleteDessert(Long idDessert);
}
