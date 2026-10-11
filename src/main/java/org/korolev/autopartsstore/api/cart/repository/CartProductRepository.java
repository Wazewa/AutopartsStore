package org.korolev.autopartsstore.api.cart.repository;

import org.korolev.autopartsstore.api.cart.entity.CartProductEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartProductRepository extends JpaRepository<CartProductEntity, Long> {
    @EntityGraph(attributePaths = "product")
    List<CartProductEntity> findByCartId(Long cartId);

    @EntityGraph(attributePaths = "product")
    Optional<CartProductEntity> findByCartIdAndProductId(Long cartId, Long id);
}
