package org.korolev.autopartsstore.api.cart.repository;

import java.util.List;
import java.util.Optional;
import org.korolev.autopartsstore.api.cart.entity.CartEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<CartEntity, Long> {
    @EntityGraph(attributePaths = "user")
    List<CartEntity> findAll();

    @EntityGraph(attributePaths = "user")
    Optional<CartEntity> findById(Long id);

    @EntityGraph(attributePaths = "user")
    Optional<CartEntity> findByUserId(Long userId);
}
