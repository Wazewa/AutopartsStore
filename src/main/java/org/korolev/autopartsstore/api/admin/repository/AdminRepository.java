package org.korolev.autopartsstore.api.admin.repository;

import org.korolev.autopartsstore.api.admin.entity.AdminEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<AdminEntity, Long> {
    boolean existsByEmail(String email);
}
