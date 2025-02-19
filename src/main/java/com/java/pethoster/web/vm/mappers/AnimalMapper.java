package com.java.pethoster.web.vm.mappers;
import  com.java.pethoster.domain.Animal;

import com.java.pethoster.domain.Utilisateur;
import com.java.pethoster.web.vm.request.AnimalRequest;
import com.java.pethoster.web.vm.response.AnimalResponse;
import org.springframework.stereotype.Component;

@Component
public class AnimalMapper {
    public Animal toEntity(AnimalRequest request, Utilisateur proprietaire) {
        Animal animal = new Animal();
        animal.setNom(request.getNom());
        animal.setRace(request.getRace());
        animal.setAge(request.getAge());
        animal.setProprietaire(proprietaire);
        animal.setBesoinsSpecifiques(request.getBesoinsSpecifiques());
        animal.setCarnetVaccination(request.getCarnetVaccination());
        animal.setPhotos(request.getPhotos());
        return animal;
    }

    public AnimalResponse toResponse(Animal animal) {
        AnimalResponse response = new AnimalResponse();
        response.setId(animal.getId());
        response.setNom(animal.getNom());
        response.setRace(animal.getRace());
        response.setAge(animal.getAge());
        response.setProprietaireId(animal.getProprietaire().getId());
        response.setBesoinsSpecifiques(animal.getBesoinsSpecifiques());
        response.setCarnetVaccination(animal.getCarnetVaccination());
        response.setPhotos(animal.getPhotos());
        return response;
    }
}