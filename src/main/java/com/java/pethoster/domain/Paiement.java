package com.java.pethoster.domain;
import com.java.pethoster.domain.enums.StatutPaiement;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;


@Entity
@Getter
@Setter
public class Paiement {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private Double montant;
    private LocalDate datePaiement;

    @Enumerated(EnumType.STRING)
    private StatutPaiement statutPaiement;

    private String methodePaiement;

    @OneToOne
    private Reservation reservation;
}