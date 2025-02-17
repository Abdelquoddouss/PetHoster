package com.java.pethoster.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Getter
@Setter
public class Avis {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private Integer note;
    private String commentaire;
    private LocalDate dateAvis;

    @ManyToOne
    private Utilisateur proprietaire;

    @ManyToOne
    private Utilisateur hebergeur;
}