package com.impact.backend.controller;

import com.impact.backend.entity.PhoneDetails;
import com.impact.backend.exception.PhoneDetailsNotFoundException;
import com.impact.backend.repository.PhoneDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/phonedetails")
public class PhoneBookController {

    @Autowired
    PhoneDetailsRepository phoneDetailsRepository;

//    Runnable to load all the PB directory data from a file.
    Runnable runnable = () -> {
        try (BufferedReader reader = new BufferedReader(new FileReader("/Users/deepkulshreshtha/Downloads/pb_directory.csv"))) {
            String line = null;
            while ((line = reader.readLine()) != null) {

                String[] array = line.split(",");
                PhoneDetails phoneDetails = new PhoneDetails();
                phoneDetails.setName(array[0]);
                phoneDetails.setEmail(array[1]);
                phoneDetails.setNumber(array[2]);
                phoneDetailsRepository.save(phoneDetails);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    };

//    Data will be loaded in a separate thread to keep application startup fast.
    @PostConstruct
    public void init() {
        new Thread(runnable).start();
    }

//    Below are all the CRUD methods. These are self explanatory
    @GetMapping("/all")
    public List<PhoneDetails> retrieveAllPhoneDetails() {
        return phoneDetailsRepository.findAll();
    }

    @GetMapping("{id}")
    public PhoneDetails retrievePhoneDetails(@PathVariable long id) throws PhoneDetailsNotFoundException {
        Optional<PhoneDetails> phoneDetails = phoneDetailsRepository.findById(id);

        if (!phoneDetails.isPresent())
            throw new PhoneDetailsNotFoundException("id-" + id);

        return phoneDetails.get();
    }

    @DeleteMapping("{id}")
    public void deletePhoneDetails(@PathVariable long id) {
        phoneDetailsRepository.deleteById(id);
    }

    @PostMapping
    public ResponseEntity<Object> createPhoneDetails(@RequestBody PhoneDetails phoneDetails) {
        PhoneDetails savedPhone = phoneDetailsRepository.save(phoneDetails);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(savedPhone.getPk()).toUri();

        return ResponseEntity.created(location).build();
    }

    @PutMapping("{id}")
    public ResponseEntity<Object> updatePhoneDetails(@RequestBody PhoneDetails phoneDetails, @PathVariable long id) {

        Optional<PhoneDetails> phoneOptional = phoneDetailsRepository.findById(id);

        if (!phoneOptional.isPresent())
            return ResponseEntity.notFound().build();

        phoneDetails.setPk(id);

        PhoneDetails savedData = phoneDetailsRepository.save(phoneDetails);
        return ResponseEntity.ok(savedData);
    }

}