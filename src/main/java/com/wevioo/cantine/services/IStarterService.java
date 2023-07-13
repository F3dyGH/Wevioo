package com.wevioo.cantine.services;

import com.wevioo.cantine.entities.Starter;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface IStarterService {

    Starter createDish(Starter starter, MultipartFile file) throws IOException;

    Starter updateDish(Long id, Starter newStarter, MultipartFile file) throws IOException;

    Starter retreiveDish(Long idDish);

    List<Starter> getAll();

    @Transactional
    void deleteDish(Long idDish);
}
