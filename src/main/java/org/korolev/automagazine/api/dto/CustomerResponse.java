package org.korolev.automagazine.api.dto;

public record CustomerResponse(
    String name,
    String surname,
    String patronymic,
    String email,
    String phone
) {}
