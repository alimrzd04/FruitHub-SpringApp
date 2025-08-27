package org.example.repository;

import org.example.model.Currencies;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CurrenciesRepository extends JpaRepository<Currencies, UUID> {
    Optional<Currencies> findByName(String name);
}
