package org.korolev.autopartsstore.api.product.dto;

import java.math.BigDecimal;

public record ProductFilter(
        String name,
        String brand,
        Long categoryId,
        String article,
        BigDecimal priceFrom,
        BigDecimal priceTo,
        Boolean inStock
) {
}
