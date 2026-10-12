package org.korolev.autopartsstore.api.product.util;

import jakarta.persistence.criteria.Predicate;
import org.korolev.autopartsstore.api.product.dto.ProductFilter;
import org.korolev.autopartsstore.api.product.entity.ProductEntity;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public final class ProductSpecifications {
    private ProductSpecifications() {}

    public static Specification<ProductEntity> from(ProductFilter productFilter) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (productFilter.name() != null && !productFilter.name().isBlank()) {
                predicates.add(cb.like(cb.lower(root.get("name")),
                        "%" + productFilter.name().toLowerCase() + "%"));
            }
            if (productFilter.brand() != null && !productFilter.brand().isBlank()) {
                predicates.add(cb.equal(cb.lower(root.get("brand")), productFilter.brand().toLowerCase()));
            }
            if (productFilter.categoryId() != null) {
                predicates.add(cb.equal(root.get("category").get("id"), productFilter.categoryId()));
            }
            if (productFilter.article() != null && !productFilter.article().isBlank()) {
                predicates.add(cb.equal(cb.lower(root.get("article")), productFilter.article().toLowerCase()));
            }
            if (productFilter.priceFrom() != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("price"), productFilter.priceFrom()));
            }
            if (productFilter.priceTo() != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("price"), productFilter.priceTo()));
            }
            if (Boolean.TRUE.equals(productFilter.inStock())) {
                predicates.add(cb.greaterThan(root.get("quantity"), 0));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
