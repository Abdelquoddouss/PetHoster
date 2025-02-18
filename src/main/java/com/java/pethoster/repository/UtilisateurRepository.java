package com.java.pethoster.repository;

import com.java.pethoster.domain.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

public interface UtilisateurRepository extends JpaRepository<Utilisateur, Long> {
    Optional<Utilisateur> findByEmail(String email);
    Optional<Utilisateur> findById(UUID id);
    @Modifying
    @Query("DELETE FROM Utilisateur u WHERE u.id = :id")
    void deleteUser(@Param("id") UUID id);
}
