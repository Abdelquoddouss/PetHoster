package com.java.pethoster.service;

import com.java.pethoster.domain.Paiement;
import com.java.pethoster.domain.Reservation;
import com.java.pethoster.domain.enums.StatutPaiement;
import com.java.pethoster.domain.enums.StatutReservation;
import com.java.pethoster.exception.exps.ResourceNotFoundException;
import com.java.pethoster.repository.PaiementRepository;
import com.java.pethoster.repository.ReservationRepository;
import com.java.pethoster.web.vm.request.PaymentRequest;
import com.java.pethoster.web.vm.response.PaymentResponse;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final ReservationRepository reservationRepository;
    private final PaiementRepository paiementRepository;

    public PaymentResponse createPaymentIntent(PaymentRequest paymentRequest) throws StripeException {
        // Récupérer la réservation
        Reservation reservation = reservationRepository.findById(paymentRequest.getReservationId())
                .orElseThrow(() -> new ResourceNotFoundException("Réservation non trouvée"));

        // Créer un PaymentIntent avec Stripe
        PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
                .setAmount(paymentRequest.getAmount())
                .setCurrency(paymentRequest.getCurrency())
                .putMetadata("reservationId", reservation.getId().toString())
                .build();

        PaymentIntent paymentIntent = PaymentIntent.create(params);

        // Associer le paymentIntentId à la réservation
        reservation.setPaymentIntentId(paymentIntent.getId());
        reservationRepository.save(reservation);

        // Enregistrer le paiement dans la base de données
        Paiement paiement = new Paiement();
        paiement.setMontant(paymentRequest.getAmount() / 100.0);
        paiement.setDatePaiement(LocalDate.now());
        paiement.setStatutPaiement(StatutPaiement.EN_ATTENTE);
        paiement.setMethodePaiement("Stripe");
        paiement.setReservation(reservation);
        paiementRepository.save(paiement);

        return PaymentResponse.builder()
                .paymentIntentId(paymentIntent.getId())
                .clientSecret(paymentIntent.getClientSecret())
                .build();
    }

    public void handlePaymentSuccess(String paymentIntentId) {
        // Mettre à jour le statut de la réservation et du paiement après réussite
        Reservation reservation = reservationRepository.findByPaymentIntentId(paymentIntentId)
                .orElseThrow(() -> new ResourceNotFoundException("Réservation non trouvée"));

        Paiement paiement = paiementRepository.findByReservation(reservation)
                .orElseThrow(() -> new ResourceNotFoundException("Paiement non trouvé"));

        reservation.setStatut(StatutReservation.CONFIRMEE);
        paiement.setStatutPaiement(StatutPaiement.PAYE);

        reservationRepository.save(reservation);
        paiementRepository.save(paiement);
    }
}