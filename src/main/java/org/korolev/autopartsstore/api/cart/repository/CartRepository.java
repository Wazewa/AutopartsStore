package org.korolev.autopartsstore.api.cart.repository;

import java.util.Optional;
import org.korolev.autopartsstore.api.cart.entity.CartEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<CartEntity, Long> {
    Optional<CartEntity> findByCustomerId(Long customerId);
}
