package com.wevioo.cantine.services.impl;

import com.wevioo.cantine.entities.User;
import com.wevioo.cantine.repositories.UserRepository;
import com.wevioo.cantine.services.IUserService;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.UUID;

@Service
public class UserServiceImpl implements IUserService {

    private static final String UPLOAD_DIR = "uploads/users_photos/";
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    UserRepository userRepository;

    @Override
    public void updatePassword(User user, String password) {
        user.setPassword(passwordEncoder.encode(password));
        user.setResetToken(null);
        user.setResetTokenExpiration(null);
        userRepository.save(user);
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id).get();
    }

    /*@Override
    public User updateUser(Long id, User newUser, MultipartFile file) throws IOException {
        User user = userRepository.findById(id).get();
        if (user != null) {
            user.setUsername(newUser.getUsername());
            user.setFirstname(newUser.getFirstname());
            user.setLastname(newUser.getLastname());
            if (file != null) {
                byte[] photoBytes = file.getBytes();
                user.setImage(photoBytes);
            }else{
                user.setImage(user.getImage());
            }
            return userRepository.save(user);
            //return ResponseEntity.ok("User Updated");
        } else {
            return (User) ResponseEntity.notFound();
        }

    }*/
    @Override
    public User updateUser(Long id, User newUser, MultipartFile file) throws IOException {
        User user = userRepository.findById(id).orElse(null);
        if (user != null) {
            user.setUsername(newUser.getUsername());
            user.setFirstname(newUser.getFirstname());
            user.setLastname(newUser.getLastname());
            if (file != null) {
                byte[] photoBytes = file.getBytes();
                user.setImage(photoBytes);
            }
            return userRepository.save(user);
        } else {
            return (User) ResponseEntity.notFound();
        }
    }

    @Override
    public String uploadPhoto(Long id, MultipartFile file) throws IOException {
        Path uploadPath = Paths.get(UPLOAD_DIR).toAbsolutePath().normalize();
        Files.createDirectories(uploadPath);
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User Not Found"));
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        String fileExtension = FilenameUtils.getExtension(fileName);
        fileName = UUID.randomUUID() + "." + fileExtension;
        Path filePath = uploadPath.resolve(fileName);
        Files.write(filePath, file.getBytes(), StandardOpenOption.CREATE);
        user.setPhoto(fileName);
        userRepository.save(user);
        return user.getPhoto();
    }

    @Override
    public ResponseEntity<byte[]> getPhoto(String photoName) throws IOException {
        User user = userRepository.findByPhoto(photoName);
        File photoFile = new File(UPLOAD_DIR + user.getPhoto());
        byte[] photoBytes = FileUtils.readFileToByteArray(photoFile);
        //return new ResponseEntity(photoResource,headers, HttpStatus.OK);
        if (photoBytes == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, MediaType.IMAGE_JPEG_VALUE)
                .body(photoBytes);
    }
}
