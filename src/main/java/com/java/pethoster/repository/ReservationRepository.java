package com.java.pethoster.repository;

import com.java.pethoster.domain.Reservation;
import com.java.pethoster.domain.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, UUID> {

    List<Reservation> findByHebergeur(Utilisateur hebergeur);
    List<Reservation> findByProprietaire(Utilisateur proprietaire);


}
