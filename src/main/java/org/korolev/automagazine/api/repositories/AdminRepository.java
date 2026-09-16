package org.korolev.automagazine.api.repositories;

import org.korolev.automagazine.api.entities.AdminEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<AdminEntity, Long> {
    boolean existsByEmail(String email);
}
