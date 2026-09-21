package org.korolev.automagazine.api.mappers;

import org.korolev.automagazine.api.dto.CartResponse;
import org.korolev.automagazine.api.entities.CartEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CartMapper {

    @Mapping(target = "customerId", source = "customer.id")
    CartResponse toResponse(CartEntity cartEntity);
}
