package com.java.pethoster.web.vm.response;

import com.java.pethoster.domain.enums.StatutReservation;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@Builder
public class ReservationResponse {
    private UUID id;
    private LocalDate dateDebut;
    private LocalDate dateFin;
    private StatutReservation statut;
    private Integer nombreAnimaux;
    private Double montantTotal;
    private LocalDate dateReservation;
    private UUID proprietaireId; // ID du propriétaire
    private UUID hebergeurId; // ID de l'hébergeur
}