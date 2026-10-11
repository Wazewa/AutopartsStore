package org.korolev.autopartsstore.api.cart.mapper;

import org.korolev.autopartsstore.api.cart.dto.CartResponse;
import org.korolev.autopartsstore.api.cart.entity.CartEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CartMapper {

    @Mapping(target = "userId", source = "user.id")
    CartResponse toResponse(CartEntity cartEntity);
}
