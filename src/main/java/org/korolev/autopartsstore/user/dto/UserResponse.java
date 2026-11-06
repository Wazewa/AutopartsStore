package org.korolev.autopartsstore.user.dto;

public record UserResponse(
    String name,
    String surname,
    String patronymic,
    String email,
    String phone,
    String roleCode
) {}
