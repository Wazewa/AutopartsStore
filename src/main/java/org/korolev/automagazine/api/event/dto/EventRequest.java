package org.korolev.automagazine.api.event.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.korolev.automagazine.api.event.entity.EventType;

@Schema(description = "Запрос на создание события")
public record EventRequest(
        @Schema(description = "ID клиента, породившего событие", example = "1",
                requiredMode = Schema.RequiredMode.NOT_REQUIRED)
        Long customerId,

        @Schema(description = "Тип события", example = "ADD_TO_CART",
                requiredMode = Schema.RequiredMode.REQUIRED,
                allowableValues = {
                        "PAGE_VIEW", "PRODUCT_VIEW", "CATEGORY_VIEW", "SEARCH_RESULTS_VIEW",
                        "ADD_TO_CART", "REMOVE_FROM_CART", "CART_VIEWED",
                        "CHECKOUT_STARTED", "CHECKOUT_ABANDONED", "ORDER_CREATED",
                        "PAYMENT_INITIATED", "PAYMENT_COMPLETED", "PAYMENT_FAILED",
                        "LOGIN", "LOGOUT", "REGISTRATION", "SEARCH"
                })
        @NotNull(message = "Event type is required")
        EventType eventType,

        @Schema(description = "ID сессии", example = "1",
                requiredMode = Schema.RequiredMode.REQUIRED)
        @NotNull(message = "Session ID is required")
        @Positive(message = "Session ID must be positive")
        Integer sessionId
) {}