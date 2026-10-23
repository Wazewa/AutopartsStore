package org.korolev.autopartsstore.api.product.util;

import org.junit.jupiter.api.Test;
import org.korolev.autopartsstore.api.product.dto.ProductFilter;
import org.springframework.data.jpa.domain.Specification;

import static org.assertj.core.api.Assertions.assertThat;

class ProductSpecificationsTest {

    @Test
    void from_withEmptyFilter_shouldReturnNotNullSpecification() {
        ProductFilter emptyFilter = new ProductFilter(null, null, null, null, null, null, null);
        Specification<?> spec = ProductSpecifications.from(emptyFilter);
        assertThat(spec).isNotNull();
    }

    @Test
    void from_withAllFilters_shouldReturnNotNullSpecification() {
        ProductFilter fullFilter = new ProductFilter("test", "brand", 1L, "art",
                java.math.BigDecimal.TEN, java.math.BigDecimal.ONE, true);
        Specification<?> spec = ProductSpecifications.from(fullFilter);
        assertThat(spec).isNotNull();
    }
}