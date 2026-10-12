package org.korolev.autopartsstore.api.compatibility.util;

import jakarta.persistence.criteria.Predicate;
import org.korolev.autopartsstore.api.compatibility.dto.CompatibilityFilter;
import org.korolev.autopartsstore.api.compatibility.entity.CompatibilityEntity;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public final class CompatibilitySpecifications {
    private CompatibilitySpecifications() {}

    public static Specification<CompatibilityEntity> from(CompatibilityFilter compatibilityFilter) {
        return ((root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(compatibilityFilter.brand() != null && !compatibilityFilter.brand().isBlank()) {
                predicates.add(cb.equal(cb.lower(root.get("brand")), compatibilityFilter.brand().toLowerCase()));
            }
            if(compatibilityFilter.model() != null && !compatibilityFilter.model().isBlank()) {
                predicates.add(cb.equal(cb.lower(root.get("model")), compatibilityFilter.model().toLowerCase()));
            }
            if(compatibilityFilter.year() != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("yearEnd"),
                        compatibilityFilter.year()));
                predicates.add(cb.lessThanOrEqualTo(root.get("yearStart"),
                        compatibilityFilter.year()));
            }
            if(compatibilityFilter.productId() != null) {
                predicates.add(cb.equal(root.get("product").get("id"), compatibilityFilter.productId()));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        });
    }
}
