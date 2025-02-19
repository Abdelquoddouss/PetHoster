package com.java.pethoster.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Entity
@Data
public class TypeAnimal {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String animalType;

    @ManyToMany
    @JoinTable(
            name = "utilisateur_type_animal",
            joinColumns = @JoinColumn(name = "type_animal_id"),
            inverseJoinColumns = @JoinColumn(name = "utilisateur_id")
    )
    private List<Utilisateur> utilisateurs;
}
