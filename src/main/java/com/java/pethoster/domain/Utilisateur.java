package com.java.pethoster.domain;

import com.java.pethoster.domain.enums.Role;
import com.java.pethoster.domain.enums.TypeAnimal;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

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
    private String telephone;
    private String adresse;
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





    @OneToMany(mappedBy = "proprietaire")
    private List<Animal> listeAnimaux;

    @OneToMany(mappedBy = "proprietaire")
    private List<Reservation> reservationsEffectuees;

    @OneToMany(mappedBy = "proprietaire")
    private List<Avis> avisLaisses;



}