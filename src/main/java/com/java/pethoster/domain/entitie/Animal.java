package com.java.pethoster.domain.entitie;

import com.java.pethoster.domain.enums.TypeAnimal;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Entity
@Getter
@Setter
public class Animal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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