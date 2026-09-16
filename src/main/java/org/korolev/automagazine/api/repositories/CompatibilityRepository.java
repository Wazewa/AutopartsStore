package org.korolev.automagazine.repositories;

import org.korolev.automagazine.entities.CompatibilityEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompatibilityRepository extends JpaRepository<CompatibilityEntity, Long> {
}
