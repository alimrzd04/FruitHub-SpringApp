package org.example.repository;

import org.example.model.Products;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;

import java.util.UUID;

public interface ProductRepository extends JpaRepository<Products, UUID> {

    Page<Products> findAll(Pageable pageable);
    Page<Products> findByNameContainingIgnoreCase(String search, Pageable pageable);

}
