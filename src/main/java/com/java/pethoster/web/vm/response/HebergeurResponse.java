package com.java.pethoster.web.vm.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
public class HebergeurResponse {
    private UUID id;
    private String nom;
    private String prenom;
    private String email;
    private String telephone;
    private String adresse;

    private Double tarifParJour;
    private String descriptionService;
    private List<UUID> typeAnimauxAcceptesIds; // Liste des IDs des types d'animaux acceptés
    private List<String> photosHebergement;
}