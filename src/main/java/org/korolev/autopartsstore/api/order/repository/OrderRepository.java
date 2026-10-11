package org.korolev.autopartsstore.api.order.repository;

import org.korolev.autopartsstore.api.order.entity.OrderEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
    @EntityGraph(attributePaths = "user")
    List<OrderEntity> findAll();

    @EntityGraph(attributePaths = "user")
    Optional<OrderEntity> findById(Long id);

    @EntityGraph(attributePaths = "user")
    List<OrderEntity> findByUserId(Long userId);
}
