package org.korolev.autopartsstore.api.cart.mapper;

import org.korolev.autopartsstore.api.cart.dto.CartProductRequest;
import org.korolev.autopartsstore.api.cart.dto.CartProductResponse;
import org.korolev.autopartsstore.api.cart.entity.CartProductEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CartProductMapper {

    @Mapping(target = "productId", source = "product.id")
    CartProductResponse toResponse(CartProductEntity cartProductEntity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "cart", ignore = true)
    @Mapping(target = "product", ignore = true)
    @Mapping(target = "priceAddAt", ignore = true)
    CartProductEntity toEntity(CartProductRequest cartProductRequest);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "cart", ignore = true)
    @Mapping(target = "product", ignore = true)
    @Mapping(target = "priceAddAt", ignore = true)
    void updateEntity(CartProductRequest cartProductRequest, @MappingTarget CartProductEntity cartProductEntity);
}
