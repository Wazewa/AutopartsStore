package org.korolev.automagazine.api.repositories;

import org.korolev.automagazine.api.entities.OrderProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderProductRepository extends JpaRepository<OrderProductEntity, Long> {
}
