package com.java.pethoster.web.rest;


import com.java.pethoster.service.AnimalService;
import com.java.pethoster.web.vm.request.AnimalRequest;
import com.java.pethoster.web.vm.response.AnimalResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/animals")
public class AnimalController {
    private final AnimalService animalService;

    public AnimalController(AnimalService animalService) {
        this.animalService = animalService;
    }

    @PostMapping
    public AnimalResponse createAnimal(@RequestBody AnimalRequest request) {
        return animalService.createAnimal(request);
    }

    @GetMapping
    public List<AnimalResponse> getAllAnimals() {
        return animalService.getAllAnimals();
    }

    @GetMapping("/{id}")
    public AnimalResponse getAnimalById(@PathVariable UUID id) {
        return animalService.getAnimalById(id);
    }

    @PutMapping("/{id}")
    public AnimalResponse updateAnimal(@PathVariable UUID id, @RequestBody AnimalRequest request) {
        return animalService.updateAnimal(id, request);
    }

    @DeleteMapping("/{id}")
    public void deleteAnimal(@PathVariable UUID id) {
        animalService.deleteAnimal(id);
    }
}
