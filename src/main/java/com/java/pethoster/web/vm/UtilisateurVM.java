package com.java.pethoster.web.vm;

import com.java.pethoster.domain.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.antlr.v4.runtime.misc.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UtilisateurVM {
    @NotNull()
    private String nom;

    @NotNull()
    private String prenom;

    @NotNull()
    private String email;

    @NotNull()
    private Role role;
}
