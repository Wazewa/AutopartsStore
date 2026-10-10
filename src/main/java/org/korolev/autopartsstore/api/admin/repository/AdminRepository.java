package org.korolev.autopartsstore.api.admin.repository;

import org.korolev.autopartsstore.api.admin.entity.AdminEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface AdminRepository extends JpaRepository<AdminEntity, Long> {
    boolean existsByEmail(String email);

    Optional<AdminEntity> findByEmail(String email);
}
