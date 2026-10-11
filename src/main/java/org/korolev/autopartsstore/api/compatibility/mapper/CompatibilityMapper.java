package org.korolev.autopartsstore.api.compatibility.mapper;

import org.korolev.autopartsstore.api.compatibility.dto.CompatibilityRequest;
import org.korolev.autopartsstore.api.compatibility.dto.CompatibilityResponse;
import org.korolev.autopartsstore.api.compatibility.entity.CompatibilityEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CompatibilityMapper {
    @Mapping(target = "productId", source = "product.id")
    CompatibilityResponse toResponse(CompatibilityEntity compatibilityEntity);

    @Mapping(target = "product", ignore = true)
    @Mapping(target = "id", ignore = true)
    CompatibilityEntity toEntity(CompatibilityRequest compatibilityRequest);

    @Mapping(target = "product", ignore = true)
    @Mapping(target = "id", ignore = true)
    void updateEntity(CompatibilityRequest compatibilityRequest, @MappingTarget CompatibilityEntity compatibilityEntity);
}
