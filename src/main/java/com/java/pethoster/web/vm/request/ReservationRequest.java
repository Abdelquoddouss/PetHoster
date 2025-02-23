package com.java.pethoster.web.vm.request;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
public class ReservationRequest {
    private LocalDate dateDebut;
    private LocalDate dateFin;
    private Integer nombreAnimaux;
    private Double montantTotal;
    private UUID proprietaireId; // ID du propriétaire
    private UUID hebergeurId; // ID de l'hébergeur
}