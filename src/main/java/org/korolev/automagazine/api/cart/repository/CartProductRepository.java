package org.korolev.automagazine.api.cart.repository;

import org.korolev.automagazine.api.cart.entity.CartProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartProductRepository extends JpaRepository<CartProductEntity, Long> {
    List<CartProductEntity> findByCartId(Long cartId);
    Optional<CartProductEntity> findByCartIdAndProductId(Long cartId, Long id);
}
