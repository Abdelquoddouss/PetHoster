package com.java.pethoster.web.vm.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class AnimalResponse {
    private UUID id;
    private String nom;
    private String race;
    private Integer age;
    private UUID proprietaireId;
    private String besoinsSpecifiques;
    private String carnetVaccination;
    private List<String> photos;
}