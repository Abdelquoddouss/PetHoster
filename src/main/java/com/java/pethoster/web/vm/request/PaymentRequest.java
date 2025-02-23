// PaymentRequest.java
package com.java.pethoster.web.vm.request;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class PaymentRequest {
    private UUID reservationId; // ID de la réservation associée
    private Long amount; // Montant en cents (ex: 5000 = 50.00€)
    private String currency; // "eur" ou "usd"
}