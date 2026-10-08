package org.korolev.autopartsstore.api.product.repository;

import org.korolev.autopartsstore.api.product.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
    boolean existsByArticle(String article);

    boolean existsByCategoryId(Long id);
}
