package com.wevioo.cantine.services.impl;

import com.wevioo.cantine.entities.FoodAndDrinks;
import com.wevioo.cantine.entities.Reservations;
import com.wevioo.cantine.enums.Categories;
import com.wevioo.cantine.repositories.FoodAndDrinksRepository;
import com.wevioo.cantine.repositories.ReservationsRepository;
import com.wevioo.cantine.services.IFoodAndDrinksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Service
public class FoodAndDrinksServiceImpl implements IFoodAndDrinksService {

    @Autowired
    FoodAndDrinksRepository foodAndDrinksRepository;

    @Autowired
    ReservationsRepository reservationsRepository;

    @Override
    public FoodAndDrinks add(FoodAndDrinks foodAndDrinks, MultipartFile file) throws IOException {
        if (file != null) {
            byte[] photoBytes = file.getBytes();
            foodAndDrinks.setImage(photoBytes);
        } else {
            foodAndDrinks.setImage(null);
        }
        String name = foodAndDrinks.getName();

        if (isFoodOrDrinkNameUnique(name)) {

            foodAndDrinks.setName(foodAndDrinks.getName());
            foodAndDrinks.setPrice(foodAndDrinks.getPrice());
            foodAndDrinks.setDescription(foodAndDrinks.getDescription());
            foodAndDrinks.setQuantity(foodAndDrinks.getQuantity());
            foodAndDrinks.setCreationDate(LocalDateTime.now(ZoneId.of("Africa/Tunis")));
            foodAndDrinks.setCategories(foodAndDrinks.getCategories());

            return foodAndDrinksRepository.save(foodAndDrinks);
        } else {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Data exists already");
        }
    }

    @Override
    public FoodAndDrinks update(Long id, FoodAndDrinks foodAndDrinks, MultipartFile file) throws IOException {
        FoodAndDrinks foodAndDrinksNew = foodAndDrinksRepository.findById(id).orElse(null);
        String name = foodAndDrinks.getName();

        if (isFoodOrDrinkNameUnique(name)) {

            foodAndDrinksNew.setModificationDate(LocalDateTime.now(ZoneId.of("Africa/Tunis")));
            foodAndDrinksNew.setDescription(foodAndDrinks.getDescription());
            foodAndDrinksNew.setQuantity(foodAndDrinks.getQuantity());
            foodAndDrinksNew.setName(foodAndDrinks.getName());
            foodAndDrinksNew.setPrice(foodAndDrinks.getPrice());
            foodAndDrinksNew.setCategories(foodAndDrinks.getCategories());

            if (file != null) {
                byte[] photoBytes = file.getBytes();
                foodAndDrinksNew.setImage(photoBytes);
            } else {
                foodAndDrinksNew.setImage(foodAndDrinksNew.getImage());
            }
            return foodAndDrinksRepository.save(foodAndDrinksNew);
        } else {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Data exists already");
        }
    }

    @Override
    public List<FoodAndDrinks> getAll() {
        return foodAndDrinksRepository.findAll();
    }

    @Override
    public void delete(Long id) {
        FoodAndDrinks foodAndDrinks = foodAndDrinksRepository.findById(id).orElse(null);
        if (foodAndDrinks != null) {
            List<Reservations> reservations = reservationsRepository.findByFood(foodAndDrinks);
            for (Reservations reservation : reservations) {
                reservationsRepository.delete(reservation);
            }
            foodAndDrinksRepository.delete(foodAndDrinks);
        }
    }

    @Override
    public List<FoodAndDrinks> getByCategory(Categories categories) {
        return foodAndDrinksRepository.findByCategory(categories);
    }

    @Override
    public FoodAndDrinks getByName(String name) {
        return foodAndDrinksRepository.findByName(name);
    }

    public boolean isFoodOrDrinkNameUnique(String name) {
        List<FoodAndDrinks> existingData = foodAndDrinksRepository.findAll();

        for (FoodAndDrinks foodAndDrinks : existingData) {
            if (foodAndDrinks.getName().equals(name)) {
                return false;
            }
        }
        return true;
    }
}
