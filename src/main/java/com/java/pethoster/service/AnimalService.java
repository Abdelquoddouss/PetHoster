package com.java.pethoster.service;
import com.java.pethoster.domain.Animal;
import com.java.pethoster.domain.Utilisateur;
import com.java.pethoster.exception.exps.InvalidDataException;
import com.java.pethoster.exception.exps.ResourceNotFoundException;
import com.java.pethoster.repository.AnimalRepository;
import com.java.pethoster.repository.UtilisateurRepository;
import com.java.pethoster.web.vm.mappers.AnimalMapper;
import com.java.pethoster.web.vm.request.AnimalRequest;
import com.java.pethoster.web.vm.response.AnimalResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AnimalService {
    private final AnimalRepository animalRepository;
    private final UtilisateurRepository utilisateurRepository;
    private final AnimalMapper animalMapper;

    public AnimalService(AnimalRepository animalRepository, UtilisateurRepository utilisateurRepository, AnimalMapper animalMapper) {
        this.animalRepository = animalRepository;
        this.utilisateurRepository = utilisateurRepository;
        this.animalMapper = animalMapper;
    }

    public AnimalResponse createAnimal(AnimalRequest request) {
        if (request.getNom() == null || request.getNom().isEmpty()) {
            throw new InvalidDataException("Le nom de l'animal est requis");
        }

        Utilisateur proprietaire = utilisateurRepository.findById(request.getProprietaireId())
                .orElseThrow(() -> new ResourceNotFoundException("Propriétaire non trouvé avec l'ID : " + request.getProprietaireId()));



        Animal animal = animalMapper.toEntity(request, proprietaire);
        animal = animalRepository.save(animal);
        return animalMapper.toResponse(animal);
    }

    public List<AnimalResponse> getAllAnimals() {
        return animalRepository.findAll().stream()
                .map(animalMapper::toResponse)
                .collect(Collectors.toList());
    }

    public AnimalResponse getAnimalById(UUID id) {
        Animal animal = animalRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Animal introuvable"));
        return animalMapper.toResponse(animal);
    }

    public AnimalResponse updateAnimal(UUID id, AnimalRequest request) {
        Animal animal = animalRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Animal introuvable"));

        Utilisateur proprietaire = utilisateurRepository.findById(request.getProprietaireId())
                .orElseThrow(() -> new RuntimeException("Propriétaire introuvable"));

        animal.setNom(request.getNom());
        animal.setRace(request.getRace());
        animal.setAge(request.getAge());
        animal.setProprietaire(proprietaire);
        animal.setBesoinsSpecifiques(request.getBesoinsSpecifiques());
        animal.setCarnetVaccination(request.getCarnetVaccination());
        animal.setPhotos(request.getPhotos());

        animal = animalRepository.save(animal);
        return animalMapper.toResponse(animal);
    }

    public void deleteAnimal(UUID id) {
        if (!animalRepository.existsById(id)) {
            throw new RuntimeException("Animal introuvable");
        }
        animalRepository.deleteById(id);
    }
}
