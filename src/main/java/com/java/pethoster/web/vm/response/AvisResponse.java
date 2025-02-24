package com.java.pethoster.web.vm.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@Builder
public class AvisResponse {
    private UUID id;
    private Integer note;
    private String commentaire;
    private LocalDate dateAvis;
    private UUID proprietaireId; // ID du propriétaire qui a laissé l'avis
    private UUID hebergeurId; // ID de l'hébergeur qui a reçu l'avis
}