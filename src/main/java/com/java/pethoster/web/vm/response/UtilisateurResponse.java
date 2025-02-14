package com.java.pethoster.web.vm.response;

import com.java.pethoster.domain.enums.Role;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UtilisateurResponse {
    private Long id;
    private String nom;
    private String prenom;
    private String email;
    private Role role;
}