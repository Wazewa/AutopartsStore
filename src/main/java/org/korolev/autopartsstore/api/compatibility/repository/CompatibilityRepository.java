package org.korolev.autopartsstore.api.compatibility.repository;

import java.util.Optional;
import org.korolev.autopartsstore.api.compatibility.entity.CompatibilityEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompatibilityRepository extends JpaRepository<CompatibilityEntity, Long> {
    @EntityGraph(attributePaths = "product")
    List<CompatibilityEntity> findAll();

    @EntityGraph(attributePaths = "product")
    Optional<CompatibilityEntity> findById(Long id);
}
