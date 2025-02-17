package com.java.pethoster.domain;

import com.java.pethoster.domain.enums.TypeAnimal;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;


@Entity
@Getter
@Setter
public class Animal {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String nom;
    private String race;
    private Integer age;

    @Enumerated(EnumType.STRING)
    private TypeAnimal espece;

    private String besoinsSpecifiques;
    private String carnetVaccination;

    @ElementCollection
    private List<String> photos;

    @ManyToOne
    private Utilisateur proprietaire;
}