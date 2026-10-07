package org.korolev.automagazine.api.cart.dto;

import java.math.BigDecimal;

public record CartProductResponse (
    Long id,
    BigDecimal priceAddAt,
    Integer quantity,
    Long productId
) { }
