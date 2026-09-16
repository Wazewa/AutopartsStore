package org.korolev.automagazine.api.repositories;

import org.korolev.automagazine.api.entities.EventEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends JpaRepository<EventEntity, Long> {
}
