package org.example.repository;

import org.example.model.Product;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;

import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID> {
    Page <Product> findByNameContainingIgnoreCase(String name, Pageable pageable);

}
