package org.korolev.automagazine.api.cart.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Запрос на добавление продукта в корзину")
public record CartProductRequest (
        @Schema(description = "ID продукта", example = "1",
                requiredMode = Schema.RequiredMode.REQUIRED)
        @NotNull(message = "Product ID is required")
        Long productId,

        @Schema(description = "Количество продукта", example = "2",
                requiredMode = Schema.RequiredMode.REQUIRED)
        @NotNull(message = "Quantity is required")
        @Min(value = 1, message = "Quantity must be at least 1")
        Integer quantity
) { }
