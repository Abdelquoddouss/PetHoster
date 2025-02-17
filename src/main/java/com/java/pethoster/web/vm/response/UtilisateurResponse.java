package com.java.pethoster.web.vm.response;

import com.java.pethoster.domain.enums.Role;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Builder
public class UtilisateurResponse {
    private UUID id;
    private String nom;
    private String prenom;
    private String email;
    private Role role;
}