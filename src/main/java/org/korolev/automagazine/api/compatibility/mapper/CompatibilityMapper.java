package org.korolev.automagazine.api.compatibility.mapper;

import org.korolev.automagazine.api.compatibility.dto.CompatibilityRequest;
import org.korolev.automagazine.api.compatibility.dto.CompatibilityResponse;
import org.korolev.automagazine.api.compatibility.entity.CompatibilityEntity;
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
