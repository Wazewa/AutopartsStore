package org.korolev.autopartsstore.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

@Schema(description = "Запрос на создание/изменение клиента")
public record UserRequest(
        @NotBlank(message = "Name is required")
        @Size(max = 30, message = "Name must be less than 30 characters")
        String name,

        @NotBlank(message = "Surname is required")
        @Size(max = 30, message = "Surname must be less than 30 characters")
        String surname,

        @Size(max = 30, message = "Patronymic must be less than 30 characters")
        String patronymic,

        @NotBlank(message = "Email is required")
        @Email(message = "Email must be valid")
        @Size(max = 100, message = "Email must be less than 100 characters")
        String email,

        @NotBlank(message = "Password is required")
        @Size(min = 8, max = 100, message = "Password must be between 8 and 100 characters")
        String password,

        @NotBlank(message = "Role is required")
        String roleCode,

        @NotBlank(message = "Phone is required")
        @Pattern(regexp = "^\\+?[78]\\d{10}$", message = "Phone must be a valid Russian number")
        String phone
) {}