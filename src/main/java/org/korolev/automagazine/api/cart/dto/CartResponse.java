package org.korolev.automagazine.api.cart.dto;

import java.time.Instant;

public record CartResponse(
        Long id,
        Instant createdDate,
        Instant modifiedDate,
        Long customerId
) {}
