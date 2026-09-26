package org.korolev.automagazine.api.mappers;

import org.korolev.automagazine.api.dto.CartProductRequest;
import org.korolev.automagazine.api.dto.CartProductResponse;
import org.korolev.automagazine.api.entities.CartProductEntity;
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
