package org.korolev.automagazine.api.product.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

@Schema(description = "Запрос на создание/обновление товара")
public record ProductRequest(
        @Schema(description = "Название товара", example = "Масло моторное 10W-40",
                requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "Name is required")
        @Size(max = 100, message = "Name must be less than 100 characters")
        String name,

        @Schema(description = "Цена товара", example = "2100",
                requiredMode = Schema.RequiredMode.REQUIRED)
        @NotNull(message = "Price is required")
        @Positive(message = "Price must be positive")
        BigDecimal price,

        @Schema(description = "Артикул товара", example = "ц137а534",
                requiredMode = Schema.RequiredMode.NOT_REQUIRED)
        @Size(max = 100, message = "Article must be less than 100 characters")
        String article,

        @Schema(description = "Количество товара", example = "3",
                requiredMode = Schema.RequiredMode.REQUIRED)
        @NotNull(message = "Quantity is required")
        @Min(value = 0, message = "Quantity must be non-negative")
        Integer quantity,

        @Schema(description = "URL фотографии товара",
                example = "https://example.net/images/oil-pemco.jpg",
                requiredMode = Schema.RequiredMode.NOT_REQUIRED)
        @Size(max = 1024, message = "Image URL must be less than 1024 characters")
        String imageUrl,

        @Schema(description = "Бренд товара", example = "PEMCO",
                requiredMode = Schema.RequiredMode.NOT_REQUIRED)
        @Size(max = 100, message = "Brand must be less than 100 characters")
        String brand,

        @Schema(description = "Комментарий к товару", example = "Лежит слева от кассы",
                requiredMode = Schema.RequiredMode.NOT_REQUIRED)
        @Size(max = 512, message = "Comment must be less than 512 characters")
        String comment
) {}
