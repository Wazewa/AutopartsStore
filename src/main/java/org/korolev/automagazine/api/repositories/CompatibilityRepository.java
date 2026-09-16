package org.korolev.automagazine.api.repositories;

import org.korolev.automagazine.api.entities.CompatibilityEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompatibilityRepository extends JpaRepository<CompatibilityEntity, Long> {
}
