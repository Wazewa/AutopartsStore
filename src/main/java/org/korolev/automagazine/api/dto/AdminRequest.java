package org.korolev.automagazine.api.dto;

public record AdminRequest(
    String name,
    String surname,
    String patronymic,
    String email,
    String passwordHash,
    Integer accessLevel
) {}
