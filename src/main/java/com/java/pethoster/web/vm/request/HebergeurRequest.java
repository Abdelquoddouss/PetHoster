package com.java.pethoster.web.vm.request;

import com.java.pethoster.domain.enums.Role;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HebergeurRequest {
    @NotBlank(message = "Le nom est obligatoire")
    @Size(max = 50, message = "Le nom ne doit pas dépasser 50 caractères")
    private String nom;

    @NotBlank(message = "Le prénom est obligatoire")
    @Size(max = 50, message = "Le prénom ne doit pas dépasser 50 caractères")
    private String prenom;

    @NotBlank(message = "L'email est obligatoire")
    @Email(message = "L'email doit être valide")
    private String email;

    @NotBlank(message = "Le mot de passe est obligatoire")
    @Size(min = 8, message = "Le mot de passe doit contenir au moins 8 caractères")
    private String motDePasse;

    @NotBlank(message = "Le téléphone est obligatoire")
    @Pattern(regexp = "^[0-9]{10}$", message = "Le téléphone doit contenir 10 chiffres")
    private String telephone;

    @NotBlank(message = "L'adresse est obligatoire")
    @Size(max = 100, message = "L'adresse ne doit pas dépasser 100 caractères")
    private String adresse;

    @NotNull(message = "Le tarif par jour est obligatoire")
    @Positive(message = "Le tarif par jour doit être un nombre positif")
    private Double tarifParJour;

    @NotBlank(message = "La description du service est obligatoire")
    @Size(max = 500, message = "La description ne doit pas dépasser 500 caractères")
    private String descriptionService;

    @NotNull(message = "Les types d'animaux acceptés sont obligatoires")
    private List<UUID> typeAnimauxAcceptesIds; // Liste des IDs des types d'animaux acceptés

    private List<String> photosHebergement;
}