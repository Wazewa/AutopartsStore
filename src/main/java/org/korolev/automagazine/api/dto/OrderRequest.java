package org.korolev.automagazine.api.dto;

import jakarta.validation.constraints.NotNull;
import org.korolev.automagazine.api.entities.PayMethod;

public record OrderRequest (

    @NotNull(message = "Customer id is required")
    Long customerId,

    @NotNull(message = "Pay method is required")
    PayMethod payMethod
) {}
