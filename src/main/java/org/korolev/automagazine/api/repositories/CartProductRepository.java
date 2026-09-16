package org.korolev.automagazine.api.repositories;

import org.korolev.automagazine.api.entities.CartProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartProductRepository extends JpaRepository<CartProductEntity, Long> {
}
