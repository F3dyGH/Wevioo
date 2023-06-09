package com.wevioo.cantine.controllers;

import com.wevioo.cantine.entities.Starter;
import com.wevioo.cantine.services.IStarterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

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

    @PreAuthorize("hasRole('USER')" + " || hasRole('STAFF')")
    @GetMapping("/all")
    public ResponseEntity<List<Starter>> getAll() {
        List<Starter> starters =  iStarterService.getAll();
        return ResponseEntity.ok().body(starters);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteDessert(@PathVariable Long id) {
        iStarterService.deleteDish(id);
    }
}
