// PaymentResponse.java
package com.java.pethoster.web.vm.response;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentResponse {
    private String paymentIntentId; // ID du paiement Stripe
    private String clientSecret; // Secret pour confirmer le paiement côté client
}