package com.java.pethoster.web.vm.request;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class HebergeurSearchRequest {
    private String localisation; // Adresse ou ville
    private List<UUID> typeAnimauxIds; // Types d'animaux acceptés
    private Double tarifMaxParJour; // Tarif maximum par jour
    private LocalDate dateDebut; // Date de début de la réservation
    private LocalDate dateFin; // Date de fin de la réservation
}