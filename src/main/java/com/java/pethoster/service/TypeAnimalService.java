package com.java.pethoster.service;

import com.java.pethoster.domain.TypeAnimal;
import com.java.pethoster.exception.exps.ResourceNotFoundException;
import com.java.pethoster.repository.TypeAnimalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TypeAnimalService {

    private final TypeAnimalRepository typeAnimalRepository;

    public TypeAnimal getTypeAnimalById(UUID id) {
        return typeAnimalRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("TypeAnimal non trouvé avec l'ID : " + id));
    }

    public List<TypeAnimal> getAllTypeAnimals() {
        return typeAnimalRepository.findAll();
    }

    public TypeAnimal createTypeAnimal(TypeAnimal typeAnimal) {
        return typeAnimalRepository.save(typeAnimal);
    }

    public TypeAnimal updateTypeAnimal(UUID id, TypeAnimal typeAnimalDetails) {
        TypeAnimal typeAnimal = typeAnimalRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("TypeAnimal non trouvé avec l'ID : " + id));

        typeAnimal.setAnimalType(typeAnimalDetails.getAnimalType());

        return typeAnimalRepository.save(typeAnimal);
    }

    public void deleteTypeAnimal(UUID id) {
        TypeAnimal typeAnimal = typeAnimalRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("TypeAnimal non trouvé avec l'ID : " + id));

        typeAnimalRepository.delete(typeAnimal);
    }
}
