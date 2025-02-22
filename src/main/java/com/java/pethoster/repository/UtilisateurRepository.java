package com.java.pethoster.repository;

import com.java.pethoster.domain.Utilisateur;
import com.java.pethoster.domain.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UtilisateurRepository extends JpaRepository<Utilisateur, UUID> {
    Optional<Utilisateur> findByEmail(String email);

    List<Utilisateur> findByRole(Role role);

    Optional<Object> findByIdAndRole(UUID id, Role role);
}
