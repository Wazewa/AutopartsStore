package org.korolev.automagazine.api.dto;

import java.math.BigDecimal;

public record ProductResponse(
        Long id,
        String name,
        BigDecimal price,
        String article,
        Integer quantity,
        String imageUrl,
        String brand,
        String comment
) {}

