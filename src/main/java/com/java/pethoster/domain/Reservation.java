package com.java.pethoster.domain;

import com.java.pethoster.domain.enums.StatutReservation;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;


@Entity
@Getter
@Setter
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate dateDebut;
    private LocalDate dateFin;

    @Enumerated(EnumType.STRING)
    private StatutReservation statut;

    private Integer nombreAnimaux;
    private Double montantTotal;
    private LocalDate dateReservation;

    @ManyToOne
    private Utilisateur proprietaire;

    @ManyToOne
    private Utilisateur hebergeur;
}