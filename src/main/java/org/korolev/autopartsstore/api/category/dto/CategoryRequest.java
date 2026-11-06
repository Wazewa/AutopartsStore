package org.korolev.autopartsstore.api.category.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "Запроса на создание/обновление категории товара")
public record CategoryRequest (
        @NotBlank(message = "Name is required")
        @Size(max = 30, message = "Name must be less than 30 characters")
        String nameCategory,

        @Size(max = 250, message = "Description must be less than 250 characters")
        String descriptionCategory
) {}
