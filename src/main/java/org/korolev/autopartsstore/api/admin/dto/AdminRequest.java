package org.korolev.autopartsstore.api.admin.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Schema(description = "Запрос на создание/обновление админа")
public record AdminRequest(
        @Schema(description = "Имя админа", example = "Иван",
                requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "Name is required")
        @Size(max = 30, message = "Name must be less than 30 characters")
        String name,

        @Schema(description = "Фамилия админа", example = "Королев",
                requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "Surname is required")
        @Size(max = 30, message = "Surname must be less than 30 characters")
        String surname,

        @Schema(description = "Отчество админа (необязательно)", example = "Михайлович",
                requiredMode = Schema.RequiredMode.NOT_REQUIRED)
        @Size(max = 30, message = "Patronymic must be less than 30 characters")
        String patronymic,

        @Schema(description = "Почта", example = "123456@mail.ru",
                requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "Email is required")
        @Email(message = "Email must be valid")
        @Size(max = 100, message = "Email must be less than 100 characters")
        String email,

        @Schema(description = "Пароль", example = "12345678",
                requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "Password is required")
        @Size(min = 8, max = 100, message = "Password must be between 8 and 100 characters")
        String password,

        @Schema(description = "Уровень доступа", example = "1",
                requiredMode = Schema.RequiredMode.REQUIRED)
        @NotNull(message = "Access level is required")
        @Min(value = 1, message = "Access level must be at least 1")
        @Max(value = 3, message = "Access level must be at most 3")
        Integer accessLevel
) {}