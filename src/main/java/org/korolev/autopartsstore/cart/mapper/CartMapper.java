package org.korolev.autopartsstore.cart.mapper;

import org.korolev.autopartsstore.cart.dto.CartResponse;
import org.korolev.autopartsstore.cart.entity.CartEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CartMapper {

    @Mapping(target = "userId", source = "user.id")
    CartResponse toResponse(CartEntity cartEntity);
}
