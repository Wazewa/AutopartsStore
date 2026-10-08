package org.korolev.autopartsstore.api.event.repository;

import org.korolev.autopartsstore.api.event.entity.EventEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends JpaRepository<EventEntity, Long> {
}
