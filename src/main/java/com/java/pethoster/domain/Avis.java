package com.java.pethoster.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
public class Avis {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer note;
    private String commentaire;
    private LocalDate dateAvis;

    @ManyToOne
    private Utilisateur proprietaire;

    @ManyToOne
    private Utilisateur hebergeur;
}