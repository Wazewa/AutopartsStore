package org.korolev.automagazine.api.repositories;

import org.korolev.automagazine.api.entities.CartEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<CartEntity, Long> {
}
