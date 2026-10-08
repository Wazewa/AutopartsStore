package org.korolev.automagazine.api.compatibility.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Schema(description = "Запрос на создание/обновление совместимости")
public record CompatibilityRequest(
        @Schema(description = "ID продукта", example = "1",
                requiredMode = Schema.RequiredMode.REQUIRED)
        @NotNull(message = "Product ID is required")
        Long productId,

        @Schema(description = "Бренд автомобиля", example = "BMW",
                requiredMode = Schema.RequiredMode.NOT_REQUIRED)
        @Size(max = 100, message = "Brand must be less than 100 characters")
        String brand,

        @Schema(description = "Модель автомобиля", example = "X5",
                requiredMode = Schema.RequiredMode.NOT_REQUIRED)
        @Size(max = 100, message = "Model must be less than 100 characters")
        String model,

        @Schema(description = "Год начала выпуска", example = "1999",
                requiredMode = Schema.RequiredMode.NOT_REQUIRED)
        @Min(value = 1950, message = "Year start must be >= 1950")
        Integer yearStart,

        @Schema(description = "Год конца выпуска", example = "2026",
                requiredMode = Schema.RequiredMode.NOT_REQUIRED)
        @Min(value = 1950, message = "Year end must be >= 1950")
        Integer yearEnd
) {}
