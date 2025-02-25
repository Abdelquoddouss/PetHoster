package com.java.pethoster.web.vm.response;

import com.java.pethoster.domain.enums.StatutReservation;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReservationResponse {
    private UUID id;
    private LocalDate dateDebut;
    private LocalDate dateFin;
    private StatutReservation statut;
    private Integer nombreAnimaux;
    private Double montantTotal;
    private LocalDate dateReservation;
    private UUID proprietaireId;
    private UUID hebergeurId;
    private String paymentIntentId;
    private String clientSecret;
}