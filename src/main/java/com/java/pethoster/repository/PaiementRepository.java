package com.java.pethoster.repository;

import com.java.pethoster.domain.Paiement;
import com.java.pethoster.domain.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PaiementRepository extends JpaRepository<Paiement, UUID> {
    Optional<Paiement> findByReservation(Reservation reservation);
}
