package com.java.pethoster.repository;

import com.java.pethoster.domain.TypeAnimal;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TypeAnimalRepository extends JpaRepository<TypeAnimal, UUID> {
    List<TypeAnimal> findAllByIdIn(List<UUID> ids);
}
