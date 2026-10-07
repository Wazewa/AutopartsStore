package org.korolev.automagazine.api.category.mapper;

import org.korolev.automagazine.api.category.dto.CategoryRequest;
import org.korolev.automagazine.api.category.dto.CategoryResponse;
import org.korolev.automagazine.api.category.entity.CategoryEntity;
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
