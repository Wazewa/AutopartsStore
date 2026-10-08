package org.korolev.autopartsstore.api.customer.repository;

import org.korolev.autopartsstore.api.customer.entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<CustomerEntity, Long> {
    boolean existsByEmail(String email);
}
