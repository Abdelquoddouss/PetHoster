package com.java.pethoster.service;

import com.java.pethoster.domain.Utilisateur;
import com.java.pethoster.exception.exps.ResourceNotFoundException;
import com.java.pethoster.repository.UtilisateurRepository;
import com.java.pethoster.web.vm.mappers.UtilisateurMapper;
import com.java.pethoster.web.vm.request.UtilisateurRequest;
import com.java.pethoster.web.vm.response.UtilisateurResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UtilisateurService {

    private final UtilisateurRepository utilisateurRepository;
    private final UtilisateurMapper utilisateurMapper;

    public UtilisateurResponse getUtilisateurById(UUID id) {
        Utilisateur utilisateur = utilisateurRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur non trouvé avec l'ID : " + id));
        return utilisateurMapper.toResponse(utilisateur);
    }

    public UtilisateurResponse updateUtilisateur(UUID id, UtilisateurRequest utilisateurRequest) {
        Utilisateur utilisateur = utilisateurRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur non trouvé avec l'ID : " + id));

        utilisateur.setNom(utilisateurRequest.getNom());
        utilisateur.setPrenom(utilisateurRequest.getPrenom());
        utilisateur.setEmail(utilisateurRequest.getEmail());
        utilisateur.setTelephone(utilisateurRequest.getTelephone());
        utilisateur.setAdresse(utilisateurRequest.getAdresse());
        utilisateur.setRole(utilisateurRequest.getRole());

        Utilisateur updatedUtilisateur = utilisateurRepository.save(utilisateur);
        return utilisateurMapper.toResponse(updatedUtilisateur);
    }

    @Transactional
    public void deleteUser(UUID id) {
        Utilisateur utilisateur = utilisateurRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur non trouvé avec l'ID : " + id));
        utilisateurRepository.delete(utilisateur);
    }
}