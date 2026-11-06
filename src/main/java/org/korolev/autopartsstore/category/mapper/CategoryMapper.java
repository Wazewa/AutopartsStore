package org.korolev.autopartsstore.category.mapper;

import org.korolev.autopartsstore.category.dto.CategoryRequest;
import org.korolev.autopartsstore.category.dto.CategoryResponse;
import org.korolev.autopartsstore.category.entity.CategoryEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    CategoryResponse toResponse(CategoryEntity categoryEntity);

    @Mapping(target = "id", ignore = true)
    CategoryEntity toEntity(CategoryRequest categoryRequest);

    @Mapping(target = "id", ignore = true)
    void updateEntity(CategoryRequest categoryRequest, @MappingTarget CategoryEntity categoryEntity);
}
