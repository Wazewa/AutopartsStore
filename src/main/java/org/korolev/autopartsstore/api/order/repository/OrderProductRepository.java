package org.korolev.autopartsstore.api.order.repository;

import org.korolev.autopartsstore.api.order.entity.OrderProductEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderProductRepository extends JpaRepository<OrderProductEntity, Long> {
    @EntityGraph(attributePaths = "product")
    List<OrderProductEntity> findByOrderId(Long orderId);

    @EntityGraph(attributePaths = "product")
    Optional<OrderProductEntity> findByOrderIdAndProductId(Long orderId, Long productId);
}
