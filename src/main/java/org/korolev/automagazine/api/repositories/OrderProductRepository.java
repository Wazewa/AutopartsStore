package org.korolev.automagazine.api.repositories;

import org.korolev.automagazine.api.entities.OrderProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderProductRepository extends JpaRepository<OrderProductEntity, Long> {
    List<OrderProductEntity> findByOrderId(Long orderId);
    Optional<OrderProductEntity> findByOrderIdAndProductId(Long orderId, Long productId);
}
