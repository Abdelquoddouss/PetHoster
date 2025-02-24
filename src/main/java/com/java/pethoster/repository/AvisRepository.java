package com.java.pethoster.repository;

import com.java.pethoster.domain.Avis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AvisRepository extends JpaRepository<Avis, UUID> {
    List<Avis> findByHebergeurId(UUID hebergeurId);
    List<Avis> findByProprietaireId(UUID proprietaireId);
}