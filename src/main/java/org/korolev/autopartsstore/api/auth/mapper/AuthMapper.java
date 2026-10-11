package org.korolev.autopartsstore.api.auth.mapper;

import org.korolev.autopartsstore.api.auth.dto.RegisterRequest;
import org.korolev.autopartsstore.api.user.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AuthMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "registeredAt", ignore = true)
    @Mapping(target = "orders", ignore = true)
    @Mapping(target = "events", ignore = true)
    @Mapping(target = "passwordHash", ignore = true)
    UserEntity toEntity(RegisterRequest registerRequest);
}
