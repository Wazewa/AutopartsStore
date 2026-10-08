package org.korolev.automagazine.api.category.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "Запроса на создание/обновление категории товара")
public record CategoryRequest (
        @Schema(description = "Категория товаров", example = "Масла",
                requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "Name is required")
        @Size(max = 30, message = "Name must be less than 30 characters")
        String name,

        @Schema(description = "Описание категории товаров", example = "Моторные масла для автомобилей, " +
                "лодок, мотоциклов или косилок",
                requiredMode = Schema.RequiredMode.NOT_REQUIRED)
        @Size(max = 250, message = "Description must be less than 250 characters")
        String description
) {}
