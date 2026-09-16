package org.korolev.automagazine.api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record AdminRequest(

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

        @NotNull(message = "Access level is required")
        @Min(value = 1, message = "Access level must be at least 1")
        @Max(value = 3, message = "Access level must be at most 3")
        Integer accessLevel
) {}