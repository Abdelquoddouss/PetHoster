package com.java.pethoster.web.rest;

import com.java.pethoster.domain.Utilisateur;
import com.java.pethoster.service.AuthService;
import com.java.pethoster.web.vm.mappers.UtilisateurMapper;
import com.java.pethoster.web.vm.request.RegisterRequest;
import com.java.pethoster.web.vm.response.UtilisateurResponse;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final UtilisateurMapper utilisateurMapper;

    @PostMapping("/register")
    public ResponseEntity<UtilisateurResponse> register(@RequestBody @Validated  RegisterRequest request) {
        Utilisateur utilisateur = utilisateurMapper.toEntity(request);
        authService.saveUtilisateur(utilisateur);
        return ResponseEntity.ok(utilisateurMapper.toResponse(utilisateur));
    }

}