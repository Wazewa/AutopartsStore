package org.korolev.automagazine.api.order.mapper;

import org.korolev.automagazine.api.order.dto.OrderProductRequest;
import org.korolev.automagazine.api.order.dto.OrderProductResponse;
import org.korolev.automagazine.api.order.entity.OrderProductEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface OrderProductMapper {

    @Mapping(target = "productId", source = "product.id")
    OrderProductResponse toResponse(OrderProductEntity orderProductEntity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "priceAddAt", ignore = true)
    @Mapping(target = "order", ignore = true)
    @Mapping(target = "product", ignore = true)
    OrderProductEntity toEntity(OrderProductRequest orderProductRequest);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "priceAddAt", ignore = true)
    @Mapping(target = "order", ignore = true)
    @Mapping(target = "product", ignore = true)
    void updateEntity(OrderProductRequest orderProductRequest, @MappingTarget OrderProductEntity orderProductEntity);
}
