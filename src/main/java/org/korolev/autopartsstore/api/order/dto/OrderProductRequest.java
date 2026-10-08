package org.korolev.autopartsstore.api.order.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Запрос на добавление товара в заказ")
public record OrderProductRequest (
        @Schema(description = "ID товара", example = "1",
                requiredMode = Schema.RequiredMode.REQUIRED)
        @NotNull(message = "Product ID is required")
        Long productId,

        @Schema(description = "Количество товара", example = "2",
                requiredMode = Schema.RequiredMode.REQUIRED)
        @NotNull(message = "Quantity is required")
        @Min(value = 1, message = "Quantity must be at least 1")
        Integer quantity
) { }
