package org.korolev.autopartsstore.api.customer.repository;

import org.korolev.autopartsstore.api.customer.entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<CustomerEntity, Long> {
    boolean existsByEmail(String email);

    Optional<CustomerEntity> findByEmail(String email);
}
