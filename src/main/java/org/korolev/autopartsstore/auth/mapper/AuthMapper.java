package org.korolev.autopartsstore.auth.mapper;

import org.korolev.autopartsstore.auth.dto.RegisterRequest;
import org.korolev.autopartsstore.user.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AuthMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "registeredAt", ignore = true)
    @Mapping(target = "role", ignore = true)
    @Mapping(target = "passwordHash", ignore = true)
    UserEntity toEntity(RegisterRequest registerRequest);
}
