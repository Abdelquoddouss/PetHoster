package com.java.pethoster.web.rest;

import com.java.pethoster.service.AvisService;
import com.java.pethoster.web.vm.request.AvisRequest;
import com.java.pethoster.web.vm.response.AvisResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/avis")
@RequiredArgsConstructor
public class AvisController {
    private final AvisService avisService;

    // Créer un avis
    @PostMapping
    public ResponseEntity<AvisResponse> createAvis(@RequestBody AvisRequest avisRequest) {
        AvisResponse avisResponse = avisService.createAvis(avisRequest);
        return ResponseEntity.ok(avisResponse);
    }

    // Mettre à jour un avis
    @PutMapping("/{id}")
    public ResponseEntity<AvisResponse> updateAvis(
            @PathVariable UUID id,
            @RequestBody AvisRequest avisRequest) {
        AvisResponse avisResponse = avisService.updateAvis(id, avisRequest);
        return ResponseEntity.ok(avisResponse);
    }

    // Supprimer un avis
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAvis(@PathVariable UUID id) {
        avisService.deleteAvis(id);
        return ResponseEntity.noContent().build();
    }

    // Obtenir tous les avis d'un hébergeur
    @GetMapping("/hebergeur/{hebergeurId}")
    public ResponseEntity<List<AvisResponse>> getAvisByHebergeur(@PathVariable UUID hebergeurId) {
        List<AvisResponse> avisResponses = avisService.getAvisByHebergeur(hebergeurId);
        return ResponseEntity.ok(avisResponses);
    }

    // Obtenir tous les avis d'un propriétaire
    @GetMapping("/proprietaire/{proprietaireId}")
    public ResponseEntity<List<AvisResponse>> getAvisByProprietaire(@PathVariable UUID proprietaireId) {
        List<AvisResponse> avisResponses = avisService.getAvisByProprietaire(proprietaireId);
        return ResponseEntity.ok(avisResponses);
    }
}