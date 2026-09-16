package org.korolev.automagazine.api.dto;

import jakarta.validation.constraints.*;

public record CustomerRequest(

        @NotBlank(message = "Name is required")
        @Size(max = 30, message = "Name must be less than 30 characters")
        String name,

        @NotBlank(message = "Surname is required")
        @Size(max = 30, message = "Surname must be less than 30 characters")
        String surname,

        @NotBlank(message = "Patronymic is required")
        @Size(max = 30, message = "Patronymic must be less than 30 characters")
        String patronymic,

        @NotBlank(message = "Email is required")
        @Email(message = "Email must be valid")
        @Size(max = 100, message = "Email must be less than 100 characters")
        String email,

        @NotBlank(message = "Password is required")
        @Size(min = 8, max = 100, message = "Password must be between 8 and 100 characters")
        String password,

        @NotBlank(message = "Phone is required")
        @Pattern(regexp = "^\\+?[78]\\d{10}$", message = "Phone must be a valid Russian number")
        String phone
) {}