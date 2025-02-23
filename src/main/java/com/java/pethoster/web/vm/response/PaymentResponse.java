// PaymentResponse.java
package com.java.pethoster.web.vm.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class PaymentResponse {
    private String paymentIntentId; // ID du paiement Stripe
    private String clientSecret; // Secret pour confirmer le paiement côté client
}