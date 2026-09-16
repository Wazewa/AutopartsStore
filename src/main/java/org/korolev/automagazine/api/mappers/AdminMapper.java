package org.korolev.automagazine.api.mappers;

import org.korolev.automagazine.api.dto.AdminRequest;
import org.korolev.automagazine.api.dto.AdminResponse;
import org.korolev.automagazine.api.entities.AdminEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface AdminMapper {

    @Mapping(target = "passwordHash", ignore = true)
    AdminResponse toResponse(AdminEntity adminEntity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "registeredAt", ignore = true)
    @Mapping(target = "accessLevel", ignore = true)
    @Mapping(target = "orders", ignore = true)
    AdminEntity toEntity(AdminRequest adminRequest);

    void updateEntity(AdminRequest adminRequest, @MappingTarget AdminEntity adminEntity);
}
