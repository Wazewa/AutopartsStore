package org.korolev.autopartsstore.api.category.repository;

import org.korolev.autopartsstore.api.category.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {
    boolean existsByName(String name);
}
