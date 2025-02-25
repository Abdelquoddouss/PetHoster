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
import com.java.pethoster.web.vm.request.ReservationRequest;
import com.java.pethoster.web.vm.response.ReservationResponse;
import com.stripe.exception.StripeException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
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
        request.setDateDebut(LocalDate.now());
        request.setDateFin(LocalDate.now().plusDays(5));
        request.setProprietaireId(UUID.randomUUID());
        request.setHebergeurId(UUID.randomUUID());

        Utilisateur proprietaire = new Utilisateur();
        Utilisateur hebergeur = new Utilisateur();

        when(utilisateurRepository.findById(request.getProprietaireId())).thenReturn(Optional.of(proprietaire));
        when(utilisateurRepository.findById(request.getHebergeurId())).thenReturn(Optional.of(hebergeur));
        when(reservationRepository.save(any(Reservation.class))).thenReturn(new Reservation());
        when(reservationMapper.toResponse(any(Reservation.class))).thenReturn(
                ReservationResponse.builder().build()
        );
        // Act
        ReservationResponse response = reservationService.createReservation(request);

        // Assert
        assertNotNull(response);
        verify(reservationRepository, times(1)).save(any(Reservation.class));
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

    @Test
    public void testCreateReservation_Conflict() throws StripeException {
        // Arrange
        ReservationRequest request = new ReservationRequest();
        request.setDateDebut(LocalDate.now());
        request.setDateFin(LocalDate.now().plusDays(5));
        request.setProprietaireId(UUID.randomUUID());
        request.setHebergeurId(UUID.randomUUID());

        Utilisateur proprietaire = new Utilisateur();
        Utilisateur hebergeur = new Utilisateur();
        hebergeur.getReservationsEffectuees().add(new Reservation()); // Simuler un conflit

        when(utilisateurRepository.findById(request.getProprietaireId())).thenReturn(Optional.of(proprietaire));
        when(utilisateurRepository.findById(request.getHebergeurId())).thenReturn(Optional.of(hebergeur));

        // Act & Assert
        assertThrows(ConflictException.class, () -> reservationService.createReservation(request));
    }
}