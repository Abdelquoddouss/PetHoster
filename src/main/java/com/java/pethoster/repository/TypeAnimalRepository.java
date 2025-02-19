package com.java.pethoster.repository;

import com.java.pethoster.domain.TypeAnimal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TypeAnimalRepository extends JpaRepository<TypeAnimal, UUID> {
}
