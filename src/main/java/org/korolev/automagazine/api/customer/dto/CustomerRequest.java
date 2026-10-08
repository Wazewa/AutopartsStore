package org.korolev.automagazine.api.customer.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

@Schema(description = "Запрос на создание/изменение клиента")
public record CustomerRequest(

        @Schema(description = "Имя клиента", example = "Иван",
                requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "Name is required")
        @Size(max = 30, message = "Name must be less than 30 characters")
        String name,

        @Schema(description = "Фамилия клиента", example = "Королев",
                requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "Surname is required")
        @Size(max = 30, message = "Surname must be less than 30 characters")
        String surname,

        @Schema(description = "Отчество клиента (необязательно)", example = "Михайлович",
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

        @Schema(description = "Номер телефона", example = "89999999999",
                requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "Phone is required")
        @Pattern(regexp = "^\\+?[78]\\d{10}$", message = "Phone must be a valid Russian number")
        String phone
) {}