package org.korolev.autopartsstore.api.admin.mapper;

import org.korolev.autopartsstore.api.admin.dto.AdminRequest;
import org.korolev.autopartsstore.api.admin.dto.AdminResponse;
import org.korolev.autopartsstore.api.admin.entity.AdminEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface AdminMapper {

    AdminResponse toResponse(AdminEntity adminEntity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "registeredAt", ignore = true)
    @Mapping(target = "accessLevel", ignore = true)
    @Mapping(target = "orders", ignore = true)
    @Mapping(target = "passwordHash", source = "password")
    AdminEntity toEntity(AdminRequest adminRequest);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "registeredAt", ignore = true)
    @Mapping(target = "accessLevel", ignore = true)
    @Mapping(target = "orders", ignore = true)
    @Mapping(target = "passwordHash", source = "password")
    void updateEntity(AdminRequest adminRequest, @MappingTarget AdminEntity adminEntity);
}
