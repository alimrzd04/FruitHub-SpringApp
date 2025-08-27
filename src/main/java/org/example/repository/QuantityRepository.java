package org.example.repository;

import org.example.model.Quantities;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface QuantityRepository extends JpaRepository<Quantities, UUID> {
    Optional<Quantities> findByName(String name);
}
