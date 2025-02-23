package com.java.pethoster.web.rest;

import com.java.pethoster.service.PaymentService;
import com.java.pethoster.web.vm.request.PaymentRequest;
import com.java.pethoster.web.vm.response.PaymentResponse;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.model.Event;
import com.stripe.model.PaymentIntent;
import com.stripe.net.Webhook;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/api/paiements")
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;

    @PostMapping("/create-intent")
    public PaymentResponse createPaymentIntent(@RequestBody PaymentRequest paymentRequest) throws Exception {
        return paymentService.createPaymentIntent(paymentRequest);
    }

    // Webhook Stripe (à configurer dans le tableau de bord Stripe)
    @PostMapping("/webhook")
    public String handleStripeWebhook(@RequestBody String payload, @RequestHeader("Stripe-Signature") String sigHeader) throws SignatureVerificationException {
        // Valider la signature Stripe
        Event event = Webhook.constructEvent(payload, sigHeader, "votre_endpoint_secret");

        // Gérer l'événement de paiement réussi
        if ("payment_intent.succeeded".equals(event.getType())) {
            PaymentIntent paymentIntent = (PaymentIntent) event.getDataObjectDeserializer().getObject().orElseThrow(() -> new RuntimeException("PaymentIntent non trouvé"));
            String paymentIntentId = paymentIntent.getId();

            // Mettre à jour le statut de la réservation
            paymentService.handlePaymentSuccess(paymentIntentId);
        }

        return "Webhook traité";
    }
}