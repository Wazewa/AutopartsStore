package org.korolev.autopartsstore.api.role.repository;

import org.korolev.autopartsstore.api.role.entity.AdminRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRoleRepository extends JpaRepository<AdminRole, Long> {
}