package com.java.pethoster.service;

import com.java.pethoster.config.JwtService;
import com.java.pethoster.domain.Utilisateur;
import com.java.pethoster.exception.exps.InvalidPasswordException;
import com.java.pethoster.exception.exps.ResourceNotFoundException;
import com.java.pethoster.repository.UtilisateurRepository;
import com.java.pethoster.utils.PasswordUtil;
import com.java.pethoster.web.vm.request.AuthRequest;
import com.java.pethoster.web.vm.response.AuthResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AuthService {

    private final UtilisateurRepository utilisateurRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;



    public Utilisateur saveUtilisateur(Utilisateur utilisateur) {
        utilisateur.setMotDePasse(PasswordUtil.hashPassword(utilisateur.getMotDePasse()));
        return utilisateurRepository.save(utilisateur);
    }

    public Utilisateur login(Utilisateur user) {
        Utilisateur userEntity = utilisateurRepository.findByEmail(user.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("this email does not exist"));

        if (!PasswordUtil.checkPassword(user.getPassword(), userEntity.getPassword())) {
            throw new InvalidPasswordException("this password does not match ");
        }

        return userEntity;
    }


    public AuthResponse loginAuth(AuthRequest authRequest) {
        try {
            Utilisateur user = utilisateurRepository.findByEmail(authRequest.getEmail())
                    .orElseThrow(() -> new BadCredentialsException("Email not found. Please check your email address."));

            try {
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                authRequest.getEmail(),
                                authRequest.getPassword()
                        )
                );
            } catch (BadCredentialsException e) {
                throw new BadCredentialsException("Incorrect password. Please try again.");
            }

            var token = jwtService.generateToken(user);

            return AuthResponse.builder()
                    .token(token)
                    .build();

        } catch (BadCredentialsException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Authentication failed", e);
        }
    }

}
