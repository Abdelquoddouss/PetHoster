package com.java.pethoster.web.rest;


import com.java.pethoster.domain.TypeAnimal;
import com.java.pethoster.service.TypeAnimalService;
import com.java.pethoster.web.vm.mappers.TypeAnimalMapper;
import com.java.pethoster.web.vm.request.TypeAnimalRequest;
import com.java.pethoster.web.vm.response.TypeAnimalResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/type-animals")
@RequiredArgsConstructor
public class TypeAnimalController {

    private final TypeAnimalService typeAnimalService;
    private final TypeAnimalMapper typeAnimalMapper;

    @GetMapping("/{id}")
    public ResponseEntity<TypeAnimalResponse> getTypeAnimalById(@PathVariable UUID id) {
        TypeAnimal typeAnimal = typeAnimalService.getTypeAnimalById(id);
        return ResponseEntity.ok(typeAnimalMapper.toResponse(typeAnimal));
    }

    @GetMapping
    public ResponseEntity<List<TypeAnimalResponse>> getAllTypeAnimals() {
        List<TypeAnimalResponse> response = typeAnimalService.getAllTypeAnimals()
                .stream()
                .map(typeAnimalMapper::toResponse)
                .collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<TypeAnimalResponse> createTypeAnimal(@RequestBody TypeAnimalRequest request) {
        TypeAnimal typeAnimal = typeAnimalMapper.toEntity(request);
        TypeAnimal savedTypeAnimal = typeAnimalService.createTypeAnimal(typeAnimal);
        return ResponseEntity.ok(typeAnimalMapper.toResponse(savedTypeAnimal));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TypeAnimalResponse> updateTypeAnimal(@PathVariable UUID id, @RequestBody TypeAnimalRequest request) {
        TypeAnimal typeAnimal = typeAnimalMapper.toEntity(request);
        TypeAnimal updatedTypeAnimal = typeAnimalService.updateTypeAnimal(id, typeAnimal);
        return ResponseEntity.ok(typeAnimalMapper.toResponse(updatedTypeAnimal));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTypeAnimal(@PathVariable UUID id) {
        typeAnimalService.deleteTypeAnimal(id);
        return ResponseEntity.noContent().build();
    }
}
