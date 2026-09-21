package org.korolev.automagazine.api.mappers;

import org.korolev.automagazine.api.dto.CompatibilityRequest;
import org.korolev.automagazine.api.dto.CompatibilityResponse;
import org.korolev.automagazine.api.entities.CompatibilityEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CompatibilityMapper {

    @Mapping(target = "productId", source = "product.id")
    CompatibilityResponse toResponse(CompatibilityEntity compatibilityEntity);

    @Mapping(target = "product", ignore = true)
    CompatibilityEntity toEntity(CompatibilityRequest compatibilityRequest);

    @Mapping(target = "product", ignore = true)
    void updateEntity(CompatibilityRequest compatibilityRequest, @MappingTarget CompatibilityEntity compatibilityEntity);
}
