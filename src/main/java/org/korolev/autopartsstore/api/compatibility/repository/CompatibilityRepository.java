package org.korolev.autopartsstore.api.compatibility.repository;

import java.util.Optional;
import org.korolev.autopartsstore.api.compatibility.entity.CompatibilityEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface CompatibilityRepository extends JpaRepository<CompatibilityEntity, Long>,
        JpaSpecificationExecutor<CompatibilityEntity> {
    @EntityGraph(attributePaths = "product")
    Page<CompatibilityEntity> findAll(Specification<CompatibilityEntity> spec, Pageable pageable);

    @EntityGraph(attributePaths = "product")
    Optional<CompatibilityEntity> findById(Long id);
}
