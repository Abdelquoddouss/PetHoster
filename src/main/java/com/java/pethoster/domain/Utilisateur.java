package com.java.pethoster.domain;

import com.java.pethoster.domain.enums.Role;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Utilisateur implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String nom;
    private String prenom;
    private String email;
    private String motDePasse;
    private String telephone;
    private String adresse;
    private String photoProfil;

    @Enumerated(EnumType.STRING)
    private Role role;

    // Attributes specific to HEBERGEUR
    private Double tarifParJour;

    @ManyToMany
    @JoinTable(
            name = "utilisateur_type_animal",
            joinColumns = @JoinColumn(name = "utilisateur_id"),
            inverseJoinColumns = @JoinColumn(name = "type_animal_id")
    )
    private List<TypeAnimal> typeAnimauxAcceptes;

    private String descriptionService;

    @ElementCollection
    private List<String> photosHebergement;

    @OneToMany(mappedBy = "proprietaire", cascade = CascadeType.ALL)
    private List<Animal> listeAnimaux;

    @OneToMany(mappedBy = "proprietaire", cascade = CascadeType.ALL)
    private List<Reservation> reservationsEffectuees = new ArrayList<>();

    @OneToMany(mappedBy = "proprietaire", cascade = CascadeType.ALL)
    private List<Avis> avisLaisses;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + role.name()));
    }

    @Override
    public String getPassword() {
        return motDePasse;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
