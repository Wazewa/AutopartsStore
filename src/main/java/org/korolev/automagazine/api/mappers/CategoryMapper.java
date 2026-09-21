package org.korolev.automagazine.api.mappers;

import org.korolev.automagazine.api.dto.CategoryRequest;
import org.korolev.automagazine.api.dto.CategoryResponse;
import org.korolev.automagazine.api.entities.CategoryEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    CategoryResponse toResponse(CategoryEntity categoryEntity);

    @Mapping(target = "products", ignore = true)
    CategoryEntity toEntity(CategoryRequest categoryRequest);

    @Mapping(target = "products", ignore = true)
    void updateEntity(CategoryRequest categoryRequest, @MappingTarget CategoryEntity categoryEntity);
}
