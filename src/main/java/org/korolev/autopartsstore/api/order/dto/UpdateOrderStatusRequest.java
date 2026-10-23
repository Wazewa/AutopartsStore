package org.korolev.autopartsstore.api.order.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import org.korolev.autopartsstore.api.order.entity.OrderEvent;

public record UpdateOrderStatusRequest(
        @Schema(description = "Тип события заказа", example = "READY_FOR_PICKUP",
                requiredMode = Schema.RequiredMode.REQUIRED,
                allowableValues = {"START_PROCESSING", "READY_FOR_PICKUP", "COMPLETE", "CANCEL", "RETURN"})
        @NotNull(message = "Order event is required")
        OrderEvent event
) {}