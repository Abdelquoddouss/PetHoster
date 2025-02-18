package com.java.pethoster.web.rest;

import com.java.pethoster.domain.Utilisateur;
import com.java.pethoster.service.UtilisateurService;
import com.java.pethoster.web.vm.mappers.UtilisateurMapper;
import com.java.pethoster.web.vm.response.UtilisateurResponse;
import com.java.pethoster.web.vm.request.UtilisateurRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/user")
@AllArgsConstructor
public class UtilisateurController {

    private final UtilisateurService utilisateurService;
    private final UtilisateurMapper utilisateurMapper;

    @GetMapping("/{id}")
    public ResponseEntity<UtilisateurResponse> getUtilisateurById(@PathVariable UUID id) {
        UtilisateurResponse utilisateurResponse = utilisateurService.getUtilisateurById(id);
        return ResponseEntity.ok(utilisateurResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UtilisateurResponse> updateUtilisateur(
            @PathVariable UUID id,
            @RequestBody UtilisateurRequest utilisateurRequest) {
        UtilisateurResponse updatedUtilisateur = utilisateurService.updateUtilisateur(id, utilisateurRequest);
        return ResponseEntity.ok(updatedUtilisateur);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable UUID id) {
        utilisateurService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}