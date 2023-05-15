package com.wevioo.cantine.controllers;

import com.wevioo.cantine.entities.Starter;
import com.wevioo.cantine.services.IStarterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/staff/starter")
public class StarterController {
    @Autowired
    IStarterService iStarterService;

    @PostMapping(value = "/add", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> addDish(@ModelAttribute Starter starter, @RequestParam(value = "photo", required = false) MultipartFile file) throws IOException {
        return ResponseEntity.ok(iStarterService.createDish(starter, file));
    }
    @PutMapping(value ="/update/{id}",  consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Starter updateDish(@ModelAttribute Starter starter, @RequestParam(value = "photo", required = false) MultipartFile file, @PathVariable Long id)  throws IOException{
        return iStarterService.updateDish(id, starter, file);
    }

    @GetMapping("/{id}")
    public Starter retreiveDessert(@PathVariable Long id) {
        return iStarterService.retreiveDish(id);
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAll() {
        return iStarterService.getAll();
    }

    @DeleteMapping("/delete/{id}")
    public void deleteDessert(@PathVariable Long id) {
        iStarterService.deleteDish(id);
    }
}
