package com.java.pethoster.services;

import com.java.pethoster.domain.Reservation;
import com.java.pethoster.domain.Utilisateur;
import com.java.pethoster.domain.enums.StatutReservation;
import com.java.pethoster.exception.exps.ConflictException;
import com.java.pethoster.exception.exps.ResourceNotFoundException;
import com.java.pethoster.repository.ReservationRepository;
import com.java.pethoster.repository.UtilisateurRepository;
import com.java.pethoster.service.PaymentService;
import com.java.pethoster.service.ReservationService;
import com.java.pethoster.web.vm.mappers.ReservationMapper;
import com.java.pethoster.web.vm.request.PaymentRequest;
import com.java.pethoster.web.vm.request.ReservationRequest;
import com.java.pethoster.web.vm.response.PaymentResponse;
import com.java.pethoster.web.vm.response.ReservationResponse;
import com.stripe.exception.StripeException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ReservationServiceTest {

    @Mock
    private ReservationRepository reservationRepository;

    @Mock
    private UtilisateurRepository utilisateurRepository;

    @Mock
    private ReservationMapper reservationMapper;

    @Mock
    private PaymentService paymentService;

    @InjectMocks
    private ReservationService reservationService;
    @Test
    public void testCreateReservation_Success() throws StripeException {
        // Arrange
        ReservationRequest request = new ReservationRequest();
        request.setProprietaireId(UUID.randomUUID());
        request.setHebergeurId(UUID.randomUUID());
        request.setDateDebut(LocalDate.now().plusDays(1));
        request.setDateFin(LocalDate.now().plusDays(5));
        request.setNombreAnimaux(2);
        request.setMontantTotal(100.0); // Ensure montantTotal is set

        Utilisateur proprietaire = new Utilisateur();
        Utilisateur hebergeur = new Utilisateur();

        when(utilisateurRepository.findById(request.getProprietaireId())).thenReturn(Optional.of(proprietaire));
        when(utilisateurRepository.findById(request.getHebergeurId())).thenReturn(Optional.of(hebergeur));

        // Mock the reservationRepository.save() method to return a valid Reservation
        Reservation savedReservation = new Reservation();
        savedReservation.setId(UUID.randomUUID()); // Set a valid ID
        when(reservationRepository.save(any(Reservation.class))).thenReturn(savedReservation);

        // Mock the PaymentService to return a valid PaymentResponse
        PaymentResponse paymentResponse = new PaymentResponse();
        paymentResponse.setPaymentIntentId("pi_123");
        paymentResponse.setClientSecret("secret_123");
        when(paymentService.createPaymentIntent(any(PaymentRequest.class))).thenReturn(paymentResponse);

        // Mock the reservationMapper.toResponse() method to return a valid ReservationResponse
        ReservationResponse reservationResponse = new ReservationResponse();
        when(reservationMapper.toResponse(any(Reservation.class))).thenReturn(reservationResponse);

        // Act
        ReservationResponse response = reservationService.createReservation(request);

        // Assert
        assertNotNull(response);
        verify(reservationRepository, times(1)).save(any(Reservation.class));
    }

    @Test
    public void testCreateReservation_Conflict() throws StripeException {
        // Arrange
        ReservationRequest request = new ReservationRequest();
        request.setProprietaireId(UUID.randomUUID());
        request.setHebergeurId(UUID.randomUUID());
        request.setDateDebut(LocalDate.now());
        request.setDateFin(LocalDate.now().plusDays(5));
        request.setMontantTotal(100.0); // Set montantTotal to avoid IllegalArgumentException

        Utilisateur proprietaire = new Utilisateur();
        Utilisateur hebergeur = new Utilisateur();
        hebergeur.setReservationsEffectuees(new ArrayList<>()); // Initialize list to avoid NullPointerException

        // Create a reservation that conflicts
        Reservation existingReservation = new Reservation();
        existingReservation.setDateDebut(LocalDate.now().plusDays(1));
        existingReservation.setDateFin(LocalDate.now().plusDays(3));

        hebergeur.getReservationsEffectuees().add(existingReservation); // Simulate a conflict

        when(utilisateurRepository.findById(request.getProprietaireId())).thenReturn(Optional.of(proprietaire));
        when(utilisateurRepository.findById(request.getHebergeurId())).thenReturn(Optional.of(hebergeur));


        // Act & Assert
        assertThrows(ConflictException.class, () -> reservationService.createReservation(request));
    }

    @Test
    public void testCreateReservation_ResourceNotFound() {
        // Arrange
        ReservationRequest request = new ReservationRequest();
        request.setProprietaireId(UUID.randomUUID());

        when(utilisateurRepository.findById(request.getProprietaireId())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> reservationService.createReservation(request));
    }


}