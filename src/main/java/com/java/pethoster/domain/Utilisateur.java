package com.java.pethoster.domain;

import com.java.pethoster.domain.enums.Role;
import com.java.pethoster.domain.enums.TypeAnimal;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Utilisateur {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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

}