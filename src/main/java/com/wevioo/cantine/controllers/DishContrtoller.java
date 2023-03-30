package com.wevioo.cantine.controllers;

import com.wevioo.cantine.entities.Dish;
import com.wevioo.cantine.entities.FileDB;
import com.wevioo.cantine.services.IDishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/staff/dish")
//@PreAuthorize("hasRole('STAFF')")
public class DishContrtoller {
    @Autowired
    IDishService iDishService;

    @PostMapping(value = "/{id}/photo", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadDishPhoto(@PathVariable Long id,
                                                  @RequestParam("file") MultipartFile file) throws IOException {

        iDishService.uploadPhoto(id, file);

        return ResponseEntity.ok("File uploaded successfully!");
    }
    @GetMapping("/photo/{photoName}")
    public ResponseEntity<byte[]> getPhoto(@PathVariable("photoName") String photoName) throws IOException {
        return iDishService.getPhoto(photoName);
    }
    @PostMapping(value = "/add", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Dish> addDish(@ModelAttribute Dish dish, @RequestParam (value = "file", required = false) MultipartFile file) throws IOException {
        /*ObjectMapper om = new ObjectMapper();
        Set<FileDB> images = uploadImage(file);
        dish.setImages(images);

        try {
            dish = om.readValue((DataInput) dish, Dish.class);   //string st -> MyInput input
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        Dish savedDish = iDishService.addDish(dish, file);
        return ResponseEntity.ok(savedDish);
    }

    public Set<FileDB> uploadImage(MultipartFile[] files) throws IOException {
        Set<FileDB> images = new HashSet<>();
        for (MultipartFile file : files){
            FileDB image = new FileDB(
                    file.getOriginalFilename(),
                    file.getContentType(),
                    file.getBytes()
            );
            images.add(image);
        }
        return images;
    }
    @PutMapping(value ="/update/{id}")
    public Dish updateDish(@RequestBody Dish dish, @PathVariable Long id) {
        return iDishService.updateDish(id, dish);
    }

   /* @PutMapping("/update/{dish}/{photo}")
    public Dish updatePhoto(@PathVariable Long idDish, @PathVariable String idPhoto){
        return iDishService.updatePhoto(idDish,idPhoto);
    }*/

    @GetMapping("/{id}")
    public Dish retreiveDessert(@PathVariable Long id) {
        return iDishService.retreiveDish(id);
    }

    @GetMapping("/all")
    public List<Dish> retreiveAll() {
        return iDishService.retreiveAll();
    }

    @DeleteMapping("/delete/{id}")
    public void deleteDessert(@PathVariable Long id) {
        iDishService.deleteDish(id);
    }
}
