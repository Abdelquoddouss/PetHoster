package com.java.pethoster.web.vm.request;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class AvisRequest {
    private Integer note;
    private String commentaire;
    private UUID proprietaireId; // ID du propriétaire qui laisse l'avis
    private UUID hebergeurId; // ID de l'hébergeur qui reçoit l'avis
}