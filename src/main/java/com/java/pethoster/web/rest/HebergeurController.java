package com.java.pethoster.web.rest;

import com.java.pethoster.domain.Utilisateur;
import com.java.pethoster.service.HebergeurService;
import com.java.pethoster.web.vm.mappers.HebergeurMapper;
import com.java.pethoster.web.vm.request.HebergeurRequest;
import com.java.pethoster.web.vm.response.HebergeurResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/hebergeurs")
@RequiredArgsConstructor
public class HebergeurController {
    private final HebergeurService hebergeurService;
    private final HebergeurMapper hebergeurMapper;

    @PostMapping
    public ResponseEntity<HebergeurResponse> createHebergeur(@RequestBody HebergeurRequest hebergeurRequest) {
        // Conversion de HebergeurRequest en Utilisateur
        Utilisateur hebergeur = hebergeurMapper.toEntity(hebergeurRequest);

        // Appel du service avec l'entité Utilisateur
        HebergeurResponse hebergeurResponse = hebergeurService.createHebergeur(hebergeur, hebergeurRequest.getTypeAnimauxAcceptesIds(), hebergeurRequest.getPhotosHebergement());
        return ResponseEntity.ok(hebergeurResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<HebergeurResponse> getHebergeurById(@PathVariable UUID id) {
        HebergeurResponse hebergeurResponse = hebergeurService.getHebergeurById(id);
        return ResponseEntity.ok(hebergeurResponse);
    }

    @GetMapping
    public ResponseEntity<List<HebergeurResponse>> getAllHebergeurs() {
        List<HebergeurResponse> hebergeurs = hebergeurService.getAllHebergeurs();
        return ResponseEntity.ok(hebergeurs);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HebergeurResponse> updateHebergeur(
            @PathVariable UUID id,
            @RequestBody HebergeurRequest hebergeurRequest) {
        // Conversion de HebergeurRequest en Utilisateur
        Utilisateur hebergeur = hebergeurMapper.toEntity(hebergeurRequest);

        // Appel du service avec l'entité Utilisateur
        HebergeurResponse updatedHebergeur = hebergeurService.updateHebergeur(id, hebergeur, hebergeurRequest.getTypeAnimauxAcceptesIds(), hebergeurRequest.getPhotosHebergement());
        return ResponseEntity.ok(updatedHebergeur);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHebergeur(@PathVariable UUID id) {
        hebergeurService.deleteHebergeur(id);
        return ResponseEntity.noContent().build();
    }
}