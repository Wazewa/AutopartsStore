package org.korolev.autopartsstore.api.compatibility.repository;

import org.korolev.autopartsstore.api.compatibility.entity.CompatibilityEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompatibilityRepository extends JpaRepository<CompatibilityEntity, Long> {
}
