package org.korolev.automagazine.api.mappers;

import org.korolev.automagazine.api.dto.ProductRequest;
import org.korolev.automagazine.api.dto.ProductResponse;
import org.korolev.automagazine.api.entities.ProductEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ProductMapper {

    ProductResponse toResponse(ProductEntity productEntity);

    @Mapping(target = "category", ignore = true)
    @Mapping(target = "compatibilites", ignore = true)
    @Mapping(target = "cartProductEntities", ignore = true)
    @Mapping(target = "orderProductEntities", ignore = true)
    ProductEntity toEntity(ProductRequest productRequest);

    @Mapping(target = "category", ignore = true)
    void updateEntity(ProductRequest productRequest, @MappingTarget ProductEntity productEntity);
}
