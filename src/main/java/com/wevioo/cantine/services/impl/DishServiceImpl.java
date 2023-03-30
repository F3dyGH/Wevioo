package com.wevioo.cantine.services.impl;

import com.wevioo.cantine.entities.Dish;
import com.wevioo.cantine.repositories.DishRepository;
import com.wevioo.cantine.services.IDishService;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.UUID;

@Service
public class DishServiceImpl implements IDishService {

    private static final String UPLOAD_DIR = "uploads/dish_photos/";
    @Autowired
    DishRepository dishRepository;
    @Autowired
    ServletContext servletContext;
    @Autowired
    ResourceLoader resourceLoader;
    @Override
    public Dish addDish(Dish dish, MultipartFile file) throws IOException /*throws IOException*/{
        //iFileUploadService.storeFile(file);
//        dish.setPhoto(file.getOriginalFilename());
        Path uploadPath = Paths.get(UPLOAD_DIR).toAbsolutePath().normalize();
        Files.createDirectories(uploadPath);
        String fileExtension = FilenameUtils.getExtension(file.getOriginalFilename());
        String fileName = UUID.randomUUID() + "." + fileExtension;
        Path filePath = uploadPath.resolve(fileName);
        Files.write(filePath, file.getBytes(), StandardOpenOption.CREATE);
        Dish d = new Dish();
        d.setName(dish.getName());
        d.setPrice(dish.getPrice());
        d.setDescription(dish.getDescription());
        d.setPhoto(fileName);
        return dishRepository.save(d);
    }
    @Override
    public void uploadPhoto(Long id, MultipartFile file) throws IOException {
        // Code to save the file to a specific directory on your server
        //String realPath = servletContext.getRealPath(""); //delete ken fama mochkol w use elly tahtha
        Path uploadPath = Paths.get(UPLOAD_DIR).toAbsolutePath().normalize();
        Files.createDirectories(uploadPath);

        Dish dish = dishRepository.findById(id).orElse(null);

        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        String fileExtension = FilenameUtils.getExtension(fileName);
        fileName = UUID.randomUUID() + "." + fileExtension;
        Path filePath = uploadPath.resolve(fileName);
        Files.write(filePath, file.getBytes(), StandardOpenOption.CREATE);
        dish.setPhoto(fileName); //use hedhy if realpath aamlet mochkla w fassakh elly tahtha
      //  dish.setPhoto(UPLOAD_DIR + "/" + fileName);
        dishRepository.save(dish);
    }
    @Override
    public ResponseEntity<byte[]> getPhoto(String photoName) throws IOException{
        /*Path filePath = Paths.get(UPLOAD_DIR + photoName).normalize().toAbsolutePath();
        Resource resource;
        try {
            resource = new UrlResource(filePath.toUri());
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);*/
        ////////////////////////////////////////////////////////////////////////////////////////
        /*String realPath = servletContext.getRealPath("");
        Path uploadPath = Paths.get(realPath, UPLOAD_DIR).toAbsolutePath().normalize();
        Path photoPath = uploadPath.resolve(name);

        Resource photoResource = resourceLoader.getResource("file:" + photoPath);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);*/
        //////////////////////////////////////////////////////////////////////////////////////
        Dish dish = dishRepository.findByPhoto(photoName);
        File photoFile = new File(UPLOAD_DIR + dish.getPhoto());
        byte[] photoBytes = FileUtils.readFileToByteArray(photoFile);
        //return new ResponseEntity(photoResource,headers, HttpStatus.OK);
        if (photoBytes == null) {
            return ResponseEntity.notFound().build();
        }

       /* String contentType;
        String fileName = String.valueOf(dishRepository.findByPhoto(photoName));
        if (fileName.toLowerCase().endsWith(".jpg") || fileName.toLowerCase().endsWith(".jpeg")) {
            contentType = MediaType.IMAGE_JPEG_VALUE;
        } else if (fileName.toLowerCase().endsWith(".png")) {
            contentType = MediaType.IMAGE_PNG_VALUE;
        } else {
            return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).build();
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.valueOf(contentType));*/
        return  ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, MediaType.IMAGE_JPEG_VALUE)
                .body(photoBytes);
    } //to review
    @Override
    public Dish updateDish(Long id, Dish newDish) {
        Dish dish = dishRepository.findById(id).orElse(null);
        if (dish != null){
            dish.setDescription(newDish.getDescription());
            dish.setName(newDish.getName());
            dish.setPrice(newDish.getPrice());
            dish.setMenu(newDish.getMenu());
            return dishRepository.save(dish);
        }else{
            return (Dish) ResponseEntity.notFound();
        }
    }

   /* @Override
    public Dish updatePhoto(Long idDish, String idPhoto) {
        String photo = String.valueOf(fileDBRepository.findById(idPhoto).get());
        Dish dish = dishRepository.findById(idDish).get();
        if (dish != null){
            dish.setPhoto(photo);
            return dishRepository.save(dish);
        }else {
            return (Dish) ResponseEntity.notFound();
        }
    }
*/
    @Override
    public Dish retreiveDish(Long idDish) {
        return dishRepository.findById(idDish).orElse(null);
    }

    @Override
    public List<Dish> retreiveAll() {
        return dishRepository.findAll();
    }

    @Override
    public void deleteDish(Long idDish) {
        dishRepository.deleteById(idDish);
    }
}
