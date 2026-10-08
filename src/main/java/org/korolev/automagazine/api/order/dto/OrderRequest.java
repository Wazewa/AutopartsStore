package org.korolev.automagazine.api.order.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import org.korolev.automagazine.api.order.entity.PayMethod;

@Schema(description = "Запрос на создание заказа")
public record OrderRequest (
    @Schema(description = "ID пользователя", example = "1",
            requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "Customer id is required")
    Long customerId,

    @Schema(description = "Тип оплаты", example = "CARD",
            requiredMode = Schema.RequiredMode.REQUIRED,
            allowableValues = {"CARD", "ONLINE", "CASH"})
    @NotNull(message = "Pay method is required")
    PayMethod payMethod
) {}
