package com.java.pethoster.services;

import com.java.pethoster.domain.Animal;
import com.java.pethoster.domain.Utilisateur;
import com.java.pethoster.exception.exps.InvalidDataException;
import com.java.pethoster.exception.exps.ResourceNotFoundException;
import com.java.pethoster.repository.AnimalRepository;
import com.java.pethoster.repository.UtilisateurRepository;
import com.java.pethoster.service.AnimalService;
import com.java.pethoster.web.vm.mappers.AnimalMapper;
import com.java.pethoster.web.vm.request.AnimalRequest;
import com.java.pethoster.web.vm.response.AnimalResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AnimalServiceTest {

    @Mock
    private AnimalRepository animalRepository;

    @Mock
    private UtilisateurRepository utilisateurRepository;

    @Mock
    private AnimalMapper animalMapper;

    @InjectMocks
    private AnimalService animalService;

    @Test
    public void testCreateAnimal_Success() {
        // Arrange
        AnimalRequest request = new AnimalRequest();
        request.setNom("Buddy");
        request.setProprietaireId(UUID.randomUUID());

        Utilisateur proprietaire = new Utilisateur();
        Animal animal = new Animal();

        when(utilisateurRepository.findById(request.getProprietaireId())).thenReturn(Optional.of(proprietaire));
        when(animalMapper.toEntity(any(AnimalRequest.class), any(Utilisateur.class))).thenReturn(animal);
        when(animalRepository.save(any(Animal.class))).thenReturn(animal);
        when(animalMapper.toResponse(any(Animal.class))).thenReturn(new AnimalResponse());

        // Act
        AnimalResponse response = animalService.createAnimal(request);

        // Assert
        assertNotNull(response);
        verify(animalRepository, times(1)).save(any(Animal.class));
    }

    @Test
    public void testCreateAnimal_InvalidData() {
        // Arrange
        AnimalRequest request = new AnimalRequest();
        request.setNom(""); // Nom vide

        // Act & Assert
        assertThrows(InvalidDataException.class, () -> animalService.createAnimal(request));
    }

    @Test
    public void testCreateAnimal_ResourceNotFound() {
        // Arrange
        AnimalRequest request = new AnimalRequest();
        request.setNom("Buddy");
        request.setProprietaireId(UUID.randomUUID());

        when(utilisateurRepository.findById(request.getProprietaireId())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> animalService.createAnimal(request));
    }
}