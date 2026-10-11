package org.korolev.autopartsstore.api.event.repository;

import java.util.Optional;
import org.korolev.autopartsstore.api.event.entity.EventEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<EventEntity, Long> {
    @EntityGraph(attributePaths = "user")
    List<EventEntity> findAll();

    @EntityGraph(attributePaths = "user")
    Optional<EventEntity> findById(Long id);
}
