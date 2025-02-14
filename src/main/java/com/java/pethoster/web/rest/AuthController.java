package com.java.pethoster.web.rest;

import com.java.pethoster.domain.Utilisateur;
import com.java.pethoster.service.AuthService;
import com.java.pethoster.web.vm.mappers.UtilisateurMapper;
import com.java.pethoster.web.vm.request.RegisterRequest;
import com.java.pethoster.web.vm.response.UtilisateurResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final UtilisateurMapper utilisateurMapper;

    @PostMapping("/register")
    public ResponseEntity<UtilisateurResponse> register(@Valid @RequestBody RegisterRequest request) {
        Utilisateur utilisateur = utilisateurMapper.toEntity(request);
        authService.saveUtilisateur(utilisateur);
        return ResponseEntity.ok(utilisateurMapper.toResponse(utilisateur));
    }
}
