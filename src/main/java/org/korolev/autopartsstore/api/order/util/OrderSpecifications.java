package org.korolev.autopartsstore.api.order.util;

import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import org.korolev.autopartsstore.api.order.dto.OrderFilter;
import org.korolev.autopartsstore.api.order.entity.OrderEntity;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public final class OrderSpecifications {
    private OrderSpecifications() {}

    public static Specification<OrderEntity> from(OrderFilter orderFilter) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (orderFilter.status() != null) {
                predicates.add(cb.equal(root.get("status"), orderFilter.status()));
            }
            if (orderFilter.dateFrom() != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("orderDate"), orderFilter.dateFrom()));
            }
            if (orderFilter.dateTo() != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("orderDate"), orderFilter.dateTo()));
            }

            if (!Long.class.equals(query.getResultType()) && !long.class.equals(query.getResultType())) {
                root.fetch("user", JoinType.LEFT);
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
