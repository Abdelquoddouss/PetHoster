package com.java.pethoster.service;

import com.java.pethoster.domain.Utilisateur;
import com.java.pethoster.repository.UtilisateurRepository;
import com.java.pethoster.utils.PasswordUtil;
import org.springframework.stereotype.Service;


@Service
public class AuthService {

    private final UtilisateurRepository utilisateurRepository;

    public AuthService(UtilisateurRepository utilisateurRepository) {
        this.utilisateurRepository = utilisateurRepository;
    }

    public Utilisateur saveUtilisateur(Utilisateur utilisateur) {
        utilisateur.setMotDePasse(PasswordUtil.hashPassword(utilisateur.getMotDePasse()));
        return utilisateurRepository.save(utilisateur);
    }

}
