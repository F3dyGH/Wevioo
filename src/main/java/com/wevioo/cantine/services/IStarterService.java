package com.wevioo.cantine.services;

import com.wevioo.cantine.entities.Starter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface IStarterService {

    ResponseEntity<?> createDish(Starter starter, MultipartFile file) throws IOException;

    Starter updateDish(Long id, Starter newStarter, MultipartFile file) throws IOException;

    Starter retreiveDish(Long idDish);

    ResponseEntity<?> getAll();

    void deleteDish(Long idDish);
}
