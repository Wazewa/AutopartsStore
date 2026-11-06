package org.korolev.autopartsstore.api.compatibility.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Schema(description = "Запрос на создание/обновление совместимости")
public record CompatibilityRequest(
        @NotNull(message = "Product ID is required")
        Long productId,

        @Size(max = 100, message = "Brand must be less than 100 characters")
        String brand,

        @Size(max = 100, message = "Model must be less than 100 characters")
        String model,

        @Min(value = 1950, message = "Year start must be >= 1950")
        Integer yearStart,

        @Min(value = 1950, message = "Year end must be >= 1950")
        Integer yearEnd
) {}
