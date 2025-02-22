package com.java.pethoster.service;

import com.java.pethoster.domain.TypeAnimal;
import com.java.pethoster.domain.Utilisateur;
import com.java.pethoster.domain.enums.Role;
import com.java.pethoster.exception.exps.ResourceNotFoundException;
import com.java.pethoster.repository.TypeAnimalRepository;
import com.java.pethoster.repository.UtilisateurRepository;
import com.java.pethoster.web.vm.mappers.HebergeurMapper;
import com.java.pethoster.web.vm.response.HebergeurResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HebergeurService {
    private final UtilisateurRepository utilisateurRepository;
    private final TypeAnimalRepository typeAnimalRepository;
    private final HebergeurMapper hebergeurMapper;

    public HebergeurResponse createHebergeur(Utilisateur hebergeur, List<UUID> typeAnimauxAcceptesIds, List<String> photosHebergement) {
        // Validation des photos
        if (photosHebergement == null || photosHebergement.isEmpty()) {
            throw new IllegalArgumentException("Au moins une photo est requise");
        }

        // Vérification des types d'animaux
        List<TypeAnimal> typeAnimauxAcceptes = typeAnimalRepository.findAllById(typeAnimauxAcceptesIds);
        if (typeAnimauxAcceptes.size() != typeAnimauxAcceptesIds.size()) {
            throw new ResourceNotFoundException("Un ou plusieurs types d'animaux spécifiés n'existent pas");
        }

        // Attribution des types d'animaux et des photos à l'hébergeur
        hebergeur.setTypeAnimauxAcceptes(typeAnimauxAcceptes);
        hebergeur.setPhotosHebergement(photosHebergement);

        // Sauvegarde de l'hébergeur
        Utilisateur savedHebergeur = utilisateurRepository.save(hebergeur);
        return hebergeurMapper.toResponse(savedHebergeur);
    }

    public HebergeurResponse getHebergeurById(UUID id) {
        Utilisateur hebergeur = (Utilisateur) utilisateurRepository.findByIdAndRole(id, Role.HEBERGEUR)
                .orElseThrow(() -> new ResourceNotFoundException("Hébergeur non trouvé avec l'ID : " + id));
        return hebergeurMapper.toResponse(hebergeur);
    }

    public List<HebergeurResponse> getAllHebergeurs() {
        List<Utilisateur> hebergeurs = utilisateurRepository.findByRole(Role.HEBERGEUR);
        return hebergeurs.stream()
                .map(hebergeurMapper::toResponse)
                .collect(Collectors.toList());
    }

    public HebergeurResponse updateHebergeur(UUID id, Utilisateur hebergeur, List<UUID> typeAnimauxAcceptesIds, List<String> photosHebergement) {
        Utilisateur existingHebergeur = (Utilisateur) utilisateurRepository.findByIdAndRole(id, Role.HEBERGEUR)
                .orElseThrow(() -> new ResourceNotFoundException("Hébergeur non trouvé avec l'ID : " + id));

        // Mettre à jour les champs de base
        existingHebergeur.setNom(hebergeur.getNom());
        existingHebergeur.setPrenom(hebergeur.getPrenom());
        existingHebergeur.setEmail(hebergeur.getEmail());
        existingHebergeur.setTelephone(hebergeur.getTelephone());
        existingHebergeur.setAdresse(hebergeur.getAdresse());

        // Mettre à jour les champs spécifiques à l'hébergeur
        existingHebergeur.setTarifParJour(hebergeur.getTarifParJour());
        existingHebergeur.setDescriptionService(hebergeur.getDescriptionService());
        existingHebergeur.setPhotosHebergement(photosHebergement);

        // Mettre à jour les types d'animaux acceptés
        List<TypeAnimal> typeAnimauxAcceptes = typeAnimalRepository.findAllById(typeAnimauxAcceptesIds);
        existingHebergeur.setTypeAnimauxAcceptes(typeAnimauxAcceptes);

        // Sauvegarde des modifications
        Utilisateur updatedHebergeur = utilisateurRepository.save(existingHebergeur);
        return hebergeurMapper.toResponse(updatedHebergeur);
    }

    public void deleteHebergeur(UUID id) {
        Utilisateur hebergeur = (Utilisateur) utilisateurRepository.findByIdAndRole(id, Role.HEBERGEUR)
                .orElseThrow(() -> new ResourceNotFoundException("Hébergeur non trouvé avec l'ID : " + id));
        utilisateurRepository.delete(hebergeur);
    }
}