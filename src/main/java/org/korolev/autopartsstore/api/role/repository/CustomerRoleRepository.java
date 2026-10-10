package org.korolev.autopartsstore.api.role.repository;

import org.korolev.autopartsstore.api.role.entity.CustomerRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRoleRepository extends JpaRepository<CustomerRole, Long> {
}