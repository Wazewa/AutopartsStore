package org.korolev.autopartsstore.order.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import org.korolev.autopartsstore.order.entity.PayMethod;

@Schema(description = "Запрос на создание заказа")
public record OrderRequest (
    @NotNull(message = "Pay method is required")
    PayMethod payMethod
) {}
