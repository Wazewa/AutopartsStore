package org.korolev.automagazine.api.repositories;

import jakarta.validation.constraints.Size;
import org.korolev.automagazine.api.entities.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
    boolean existsByArticle(String article);
}
