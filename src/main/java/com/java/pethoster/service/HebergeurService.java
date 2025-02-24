package com.java.pethoster.service;

import com.java.pethoster.domain.TypeAnimal;
import com.java.pethoster.domain.Utilisateur;
import com.java.pethoster.domain.enums.Role;
import com.java.pethoster.exception.exps.ResourceNotFoundException;
import com.java.pethoster.repository.TypeAnimalRepository;
import com.java.pethoster.repository.UtilisateurRepository;
import com.java.pethoster.web.vm.mappers.HebergeurMapper;
import com.java.pethoster.web.vm.request.HebergeurSearchRequest;
import com.java.pethoster.web.vm.response.HebergeurResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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
        if (photosHebergement == null || photosHebergement.isEmpty()) {
            throw new IllegalArgumentException("Au moins une photo est requise");
        }

        List<TypeAnimal> typeAnimauxAcceptes = typeAnimalRepository.findAllById(typeAnimauxAcceptesIds);
        if (typeAnimauxAcceptes.size() != typeAnimauxAcceptesIds.size()) {
            throw new ResourceNotFoundException("Un ou plusieurs types d'animaux spécifiés n'existent pas");
        }

        hebergeur.setTypeAnimauxAcceptes(typeAnimauxAcceptes);
        hebergeur.setPhotosHebergement(photosHebergement);

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

        existingHebergeur.setNom(hebergeur.getNom());
        existingHebergeur.setPrenom(hebergeur.getPrenom());
        existingHebergeur.setEmail(hebergeur.getEmail());
        existingHebergeur.setTelephone(hebergeur.getTelephone());
        existingHebergeur.setAdresse(hebergeur.getAdresse());

        existingHebergeur.setTarifParJour(hebergeur.getTarifParJour());
        existingHebergeur.setDescriptionService(hebergeur.getDescriptionService());
        existingHebergeur.setPhotosHebergement(photosHebergement);

        List<TypeAnimal> typeAnimauxAcceptes = typeAnimalRepository.findAllById(typeAnimauxAcceptesIds);
        existingHebergeur.setTypeAnimauxAcceptes(typeAnimauxAcceptes);

        Utilisateur updatedHebergeur = utilisateurRepository.save(existingHebergeur);
        return hebergeurMapper.toResponse(updatedHebergeur);
    }

    public void deleteHebergeur(UUID id) {
        Utilisateur hebergeur = (Utilisateur) utilisateurRepository.findByIdAndRole(id, Role.HEBERGEUR)
                .orElseThrow(() -> new ResourceNotFoundException("Hébergeur non trouvé avec l'ID : " + id));
        utilisateurRepository.delete(hebergeur);
    }

    public List<HebergeurResponse> searchHebergeurs(HebergeurSearchRequest searchRequest) {
        List<Utilisateur> hebergeurs = utilisateurRepository.findByRole(Role.HEBERGEUR);

        return hebergeurs.stream()
                .filter(hebergeur -> {
                    if (searchRequest.getLocalisation() != null && !searchRequest.getLocalisation().isEmpty()) {
                        return hebergeur.getAdresse().toLowerCase().contains(searchRequest.getLocalisation().toLowerCase());
                    }
                    return true;
                })
                .filter(hebergeur -> {
                    if (searchRequest.getTypeAnimauxIds() != null && !searchRequest.getTypeAnimauxIds().isEmpty()) {
                        return hebergeur.getTypeAnimauxAcceptes().stream()
                                .anyMatch(typeAnimal -> searchRequest.getTypeAnimauxIds().contains(typeAnimal.getId()));
                    }
                    return true;
                })
                .filter(hebergeur -> {
                    if (searchRequest.getTarifMaxParJour() != null) {
                        return hebergeur.getTarifParJour() <= searchRequest.getTarifMaxParJour();
                    }
                    return true;
                })
                .filter(hebergeur -> {
                    if (searchRequest.getDateDebut() != null && searchRequest.getDateFin() != null) {
                        return isHebergeurAvailable(hebergeur, searchRequest.getDateDebut(), searchRequest.getDateFin());
                    }
                    return true;
                })
                .map(hebergeurMapper::toResponse)
                .collect(Collectors.toList());
    }


    private boolean isHebergeurAvailable(Utilisateur hebergeur, LocalDate dateDebut, LocalDate dateFin) {
        return hebergeur.getReservationsEffectuees().stream()
                .noneMatch(reservation -> reservation.getDateDebut().isBefore(dateFin) && reservation.getDateFin().isAfter(dateDebut));
    }
}