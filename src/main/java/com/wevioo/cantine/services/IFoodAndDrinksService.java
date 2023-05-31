package com.wevioo.cantine.services;

import com.wevioo.cantine.entities.FoodAndDrinks;
import com.wevioo.cantine.enums.Categories;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface IFoodAndDrinksService {

    FoodAndDrinks add(FoodAndDrinks foodAndDrinks, MultipartFile file) throws IOException;

    FoodAndDrinks update(Long id, FoodAndDrinks foodAndDrinks, MultipartFile file) throws IOException;

    List<FoodAndDrinks> getAll();

    void Delete(Long id);

    List<FoodAndDrinks> getByCategory(Categories categories);

}
