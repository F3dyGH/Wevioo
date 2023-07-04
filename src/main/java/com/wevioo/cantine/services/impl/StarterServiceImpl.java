package com.wevioo.cantine.services.impl;

import com.wevioo.cantine.entities.Starter;
import com.wevioo.cantine.repositories.ReservationsRepository;
import com.wevioo.cantine.repositories.StarterRepository;
import com.wevioo.cantine.services.IStarterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class StarterServiceImpl implements IStarterService {

    @Autowired
    StarterRepository starterRepository;
    @Autowired
    private ReservationsRepository reservationsRepository;

    @Override
    public ResponseEntity<?> createDish(Starter starter, MultipartFile file) throws IOException {
        if (file != null) {
            byte[] photoBytes = file.getBytes();
            starter.setImage(photoBytes);
        } else {
            starter.setImage(null);
        }
        starterRepository.save(starter);
        return ResponseEntity.ok("Starter created Successfully");
    }

    @Override
    public Starter updateDish(Long id, Starter newStarter, MultipartFile file) throws IOException {
        Starter starter = starterRepository.findById(id).orElse(null);
        if (starter != null) {
            starter.setDescription(newStarter.getDescription());
            starter.setName(newStarter.getName());
            starter.setPrice(newStarter.getPrice());
            if (file != null) {
                byte[] photoBytes = file.getBytes();
                starter.setImage(photoBytes);
            }
            return starterRepository.save(starter);
        } else {
            return (Starter) ResponseEntity.notFound();
        }
    }

    @Override
    public Starter retreiveDish(Long idDish) {
        return starterRepository.findById(idDish).orElse(null);
    }

    @Override
    public List<Starter> getAll() {
        return starterRepository.findAll();
    }

    @Override
    public void deleteDish(Long idDish) {
        reservationsRepository.deleteByStarterId(idDish);
        starterRepository.deleteById(idDish);
    }
}
