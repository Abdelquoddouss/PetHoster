package com.java.pethoster.service;

import com.java.pethoster.domain.Reservation;
import com.java.pethoster.domain.Utilisateur;
import com.java.pethoster.domain.enums.StatutReservation;
import com.java.pethoster.exception.exps.ResourceNotFoundException;
import com.java.pethoster.repository.ReservationRepository;
import com.java.pethoster.repository.UtilisateurRepository;
import com.java.pethoster.web.vm.mappers.ReservationMapper;
import com.java.pethoster.web.vm.request.ReservationRequest;
import com.java.pethoster.web.vm.response.ReservationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final UtilisateurRepository utilisateurRepository;
    private final ReservationMapper reservationMapper;

    public ReservationResponse createReservation(ReservationRequest reservationRequest) {
        // Récupérer le propriétaire et l'hébergeur
        Utilisateur proprietaire = utilisateurRepository.findById(reservationRequest.getProprietaireId())
                .orElseThrow(() -> new ResourceNotFoundException("Propriétaire non trouvé avec l'ID : " + reservationRequest.getProprietaireId()));

        Utilisateur hebergeur = utilisateurRepository.findById(reservationRequest.getHebergeurId())
                .orElseThrow(() -> new ResourceNotFoundException("Hébergeur non trouvé avec l'ID : " + reservationRequest.getHebergeurId()));

        // Vérifier la disponibilité de l'hébergeur
        if (!isHebergeurAvailable(hebergeur, reservationRequest.getDateDebut(), reservationRequest.getDateFin())) {
            throw new IllegalArgumentException("L'hébergeur n'est pas disponible pour les dates demandées");
        }

        // Créer la réservation
        Reservation reservation = new Reservation();
        reservation.setDateDebut(reservationRequest.getDateDebut());
        reservation.setDateFin(reservationRequest.getDateFin());
        reservation.setNombreAnimaux(reservationRequest.getNombreAnimaux());
        reservation.setMontantTotal(reservationRequest.getMontantTotal());
        reservation.setStatut(StatutReservation.EN_ATTENTE); // Statut initial
        reservation.setDateReservation(LocalDate.now()); // Date de la réservation
        reservation.setProprietaire(proprietaire);
        reservation.setHebergeur(hebergeur);

        // Sauvegarder la réservation
        Reservation savedReservation = reservationRepository.save(reservation);

        // Convertir en réponse
        return reservationMapper.toResponse(savedReservation);
    }

    private boolean isHebergeurAvailable(Utilisateur hebergeur, LocalDate dateDebut, LocalDate dateFin) {
        // Vérifier si l'hébergeur a des réservations qui chevauchent les dates demandées
        return hebergeur.getReservationsEffectuees().stream()
                .noneMatch(reservation -> reservation.getDateDebut().isBefore(dateFin) && reservation.getDateFin().isAfter(dateDebut));
    }

    public ReservationResponse annulerReservation(UUID id) {
        // Récupérer la réservation
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Réservation non trouvée avec l'ID : " + id));

        // Vérifier si la réservation peut être annulée
        if (reservation.getStatut() == StatutReservation.ANNULEE || reservation.getStatut() == StatutReservation.TERMINEE) {
            throw new IllegalArgumentException("La réservation ne peut pas être annulée car elle est déjà " + reservation.getStatut());
        }

        // Changer le statut de la réservation
        reservation.setStatut(StatutReservation.ANNULEE);

        // Sauvegarder la réservation mise à jour
        Reservation updatedReservation = reservationRepository.save(reservation);

        // Convertir en réponse
        return reservationMapper.toResponse(updatedReservation);
    }
}