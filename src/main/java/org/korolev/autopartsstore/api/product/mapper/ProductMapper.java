package org.korolev.autopartsstore.api.product.mapper;

import org.korolev.autopartsstore.api.product.dto.ProductRequest;
import org.korolev.autopartsstore.api.product.dto.ProductResponse;
import org.korolev.autopartsstore.api.product.entity.ProductEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ProductMapper {
    ProductResponse toResponse(ProductEntity productEntity);

    @Mapping(target = "category", ignore = true)
    @Mapping(target = "id", ignore = true)
    ProductEntity toEntity(ProductRequest productRequest);

    @Mapping(target = "category", ignore = true)
    @Mapping(target = "id", ignore = true)
    void updateEntity(ProductRequest productRequest, @MappingTarget ProductEntity productEntity);
}
