package com.java.pethoster.domain.entitie;
import com.java.pethoster.domain.enums.StatutPaiement;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;


@Entity
@Getter
@Setter
public class Paiement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double montant;
    private LocalDate datePaiement;

    @Enumerated(EnumType.STRING)
    private StatutPaiement statutPaiement;

    private String methodePaiement;

    @OneToOne
    private Reservation reservation;
}