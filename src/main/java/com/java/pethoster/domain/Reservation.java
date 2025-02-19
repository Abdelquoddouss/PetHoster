package com.java.pethoster.domain;

import com.java.pethoster.domain.enums.StatutReservation;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;


@Entity
@Getter
@Setter
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private LocalDate dateDebut;
    private LocalDate dateFin;

    @Enumerated(EnumType.STRING)
    private StatutReservation statut;

    private Integer nombreAnimaux;
    private Double montantTotal;
    private LocalDate dateReservation;

    @ManyToOne
    @JoinColumn(name = "proprietaire_id", nullable = false)
    private Utilisateur proprietaire;

    @ManyToOne
    @JoinColumn(name = "hebergeur_id", nullable = false)
    private Utilisateur hebergeur;
}