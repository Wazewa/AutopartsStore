package org.korolev.autopartsstore.api.user.repository;

import org.korolev.autopartsstore.api.user.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    boolean existsByEmail(String email);

    @EntityGraph(attributePaths = "role")
    Optional<UserEntity> findById(Long id);

    @EntityGraph(attributePaths = "role")
    Page<UserEntity> findAll(Pageable pageable);

    @EntityGraph(attributePaths = "role")
    Optional<UserEntity> findByEmail(String email);
}
