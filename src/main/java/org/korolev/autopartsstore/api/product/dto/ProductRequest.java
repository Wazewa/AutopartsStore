package org.korolev.autopartsstore.api.product.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

@Schema(description = "Запрос на создание/обновление товара")
public record ProductRequest(
        @NotBlank(message = "Name is required")
        @Size(max = 100, message = "Name must be less than 100 characters")
        String name,

        @NotNull(message = "Price is required")
        @Positive(message = "Price must be positive")
        BigDecimal price,

        @Size(max = 100, message = "Article must be less than 100 characters")
        String article,

        @NotNull(message = "Quantity is required")
        @Min(value = 0, message = "Quantity must be non-negative")
        Integer quantity,

        @Size(max = 1024, message = "Image URL must be less than 1024 characters")
        String imageUrl,

        @Size(max = 100, message = "Brand must be less than 100 characters")
        String brand
) {}
