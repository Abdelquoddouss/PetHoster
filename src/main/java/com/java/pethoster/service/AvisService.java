package com.java.pethoster.service;

import com.java.pethoster.domain.Avis;
import com.java.pethoster.domain.Utilisateur;
import com.java.pethoster.exception.exps.ResourceNotFoundException;
import com.java.pethoster.repository.AvisRepository;
import com.java.pethoster.repository.UtilisateurRepository;
import com.java.pethoster.web.vm.request.AvisRequest;
import com.java.pethoster.web.vm.response.AvisResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AvisService {
    private final AvisRepository avisRepository;
    private final UtilisateurRepository utilisateurRepository;

    // Créer un avis
    public AvisResponse createAvis(AvisRequest avisRequest) {
        Utilisateur proprietaire = utilisateurRepository.findById(avisRequest.getProprietaireId())
                .orElseThrow(() -> new ResourceNotFoundException("Propriétaire non trouvé"));

        Utilisateur hebergeur = utilisateurRepository.findById(avisRequest.getHebergeurId())
                .orElseThrow(() -> new ResourceNotFoundException("Hébergeur non trouvé"));

        Avis avis = new Avis();
        avis.setNote(avisRequest.getNote());
        avis.setCommentaire(avisRequest.getCommentaire());
        avis.setDateAvis(LocalDate.now());
        avis.setProprietaire(proprietaire);
        avis.setHebergeur(hebergeur);

        Avis savedAvis = avisRepository.save(avis);
        return toAvisResponse(savedAvis);
    }

    // Mettre à jour un avis
    public AvisResponse updateAvis(UUID id, AvisRequest avisRequest) {
        Avis avis = avisRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Avis non trouvé"));

        avis.setNote(avisRequest.getNote());
        avis.setCommentaire(avisRequest.getCommentaire());
        avis.setDateAvis(LocalDate.now());

        Avis updatedAvis = avisRepository.save(avis);
        return toAvisResponse(updatedAvis);
    }

    // Supprimer un avis
    public void deleteAvis(UUID id) {
        avisRepository.deleteById(id);
    }

    // Obtenir tous les avis d'un hébergeur
    public List<AvisResponse> getAvisByHebergeur(UUID hebergeurId) {
        List<Avis> avisList = avisRepository.findByHebergeurId(hebergeurId);
        return avisList.stream().map(this::toAvisResponse).collect(Collectors.toList());
    }

    // Obtenir tous les avis d'un propriétaire
    public List<AvisResponse> getAvisByProprietaire(UUID proprietaireId) {
        List<Avis> avisList = avisRepository.findByProprietaireId(proprietaireId);
        return avisList.stream().map(this::toAvisResponse).collect(Collectors.toList());
    }

    // Convertir Avis en AvisResponse
    private AvisResponse toAvisResponse(Avis avis) {
        return AvisResponse.builder()
                .id(avis.getId())
                .note(avis.getNote())
                .commentaire(avis.getCommentaire())
                .dateAvis(avis.getDateAvis())
                .proprietaireId(avis.getProprietaire().getId())
                .hebergeurId(avis.getHebergeur().getId())
                .build();
    }
}