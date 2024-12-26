package com.java.pethoster.domain.entitie;

import com.java.pethoster.domain.enums.NiveauAcces;
import com.java.pethoster.domain.enums.Role;
import com.java.pethoster.domain.enums.TypeAnimal;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
public class Utilisateur {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;
    private String prenom;
    private String email;
    private String motDePasse;
    private LocalDate dateInscription;
    private String telephone;
    private String adresse;
    private boolean actif;
    private String photoProfil;

    @Enumerated(EnumType.STRING)
    private Role role;


    // Attributs spécifiques pour HEBERGEUR
    private Double tarifParJour;
    @ElementCollection
    @Enumerated(EnumType.STRING)
    private List<TypeAnimal> typeAnimauxAcceptes;
    private String descriptionService;
    @ElementCollection
    private List<String> photosHebergement;
    private Boolean estVerifie;
    private Double moyenneNotation;

    // Attributs spécifiques pour ADMIN
    @Enumerated(EnumType.STRING)
    private NiveauAcces niveauAcces;



    @OneToMany(mappedBy = "proprietaire")
    private List<Animal> listeAnimaux;

    @OneToMany(mappedBy = "proprietaire")
    private List<Reservation> reservationsEffectuees;

    @OneToMany(mappedBy = "proprietaire")
    private List<Avis> avisLaisses;

    @OneToMany(mappedBy = "hebergeur")
    private List<Disponibilite> disponibilites;


}