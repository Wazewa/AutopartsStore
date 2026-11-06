package org.korolev.autopartsstore.order.dto;

import java.math.BigDecimal;

public record OrderProductResponse (
        Long id,
        BigDecimal priceAddAt,
        Integer quantity,
        Long productId
) { }
