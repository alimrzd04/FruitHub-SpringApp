package org.example.repository;

import org.example.model.Cart;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CartRepository extends JpaRepository<Cart, UUID> {
    @Query("SELECT c FROM Cart c WHERE c.users.uuid = :userId AND c.products.uuid = :productId")
    Optional<Cart> findByUserAndProduct(UUID userId, UUID productId);

    @Query("SELECT COALESCE(SUM(c.count), 0) FROM Cart c WHERE c.users.uuid = :userId")
    Integer getTotalProductCount(@Param("userId") UUID userId);

    Page<Cart> findAllByUsers_Uuid(UUID userId, Pageable pageable);
}
