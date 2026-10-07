package org.korolev.automagazine.api.cart.mapper;

import org.korolev.automagazine.api.cart.dto.CartResponse;
import org.korolev.automagazine.api.cart.entity.CartEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CartMapper {

    @Mapping(target = "customerId", source = "customer.id")
    CartResponse toResponse(CartEntity cartEntity);
}
