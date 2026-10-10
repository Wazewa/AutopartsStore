package org.korolev.autopartsstore.api.customer.dto;

public record CustomerResponse(
    String name,
    String surname,
    String patronymic,
    String email,
    String phone
) {}
