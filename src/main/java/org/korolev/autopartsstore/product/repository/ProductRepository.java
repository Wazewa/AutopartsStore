package org.korolev.autopartsstore.product.repository;

import org.korolev.autopartsstore.product.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long>,
        JpaSpecificationExecutor<ProductEntity> {
    boolean existsByArticle(String article);

    boolean existsByCategoryId(Long id);
}
