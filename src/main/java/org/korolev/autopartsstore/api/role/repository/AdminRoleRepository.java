package org.korolev.autopartsstore.api.role.repository;

import org.korolev.autopartsstore.api.role.entity.AdminRole;
import org.korolev.autopartsstore.api.role.entity.AdminRoleId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdminRoleRepository extends JpaRepository<AdminRole, AdminRoleId> {
    List<AdminRole> findById_AdminId(Long adminId);
}