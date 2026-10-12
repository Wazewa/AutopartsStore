package org.korolev.autopartsstore.api.event.repository;

import java.util.Optional;
import org.korolev.autopartsstore.api.event.entity.EventEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface EventRepository extends JpaRepository<EventEntity, Long> {
    @EntityGraph(attributePaths = "user")
    Page<EventEntity> findAll(Pageable pageable);

    @EntityGraph(attributePaths = "user")
    Optional<EventEntity> findById(Long id);
}
