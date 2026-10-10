package org.korolev.autopartsstore.api.role.repository;

import org.korolev.autopartsstore.api.role.entity.CustomerRole;
import org.korolev.autopartsstore.api.role.entity.CustomerRoleId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRoleRepository extends JpaRepository<CustomerRole, CustomerRoleId> {
    List<CustomerRole> findById_CustomerId(Long customerId);
}