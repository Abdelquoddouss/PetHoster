package com.java.pethoster.domain;

import com.java.pethoster.domain.enums.Role;
import com.java.pethoster.domain.enums.TypeAnimal;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

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


    // Attributs spécifiques pour HEBERGEUR
    private Double tarifParJour;
    @ElementCollection
    @CollectionTable(name = "utilisateur_animaux_acceptes", joinColumns = @JoinColumn(name = "utilisateur_id"))
    @Enumerated(EnumType.STRING)
    private List<TypeAnimal> typeAnimauxAcceptes;

    private String descriptionService;
    @ElementCollection
    private List<String> photosHebergement;





    @OneToMany(mappedBy = "proprietaire")
    private List<Animal> listeAnimaux;

    @OneToMany(mappedBy = "proprietaire")
    private List<Reservation> reservationsEffectuees;

    @OneToMany(mappedBy = "proprietaire")
    private List<Avis> avisLaisses;


    public String getMotDePasse() {
        return motDePasse;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }



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