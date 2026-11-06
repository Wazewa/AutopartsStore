package org.korolev.autopartsstore.user.mapper;

import org.korolev.autopartsstore.user.dto.UserRequest;
import org.korolev.autopartsstore.user.dto.UserResponse;
import org.korolev.autopartsstore.user.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "roleCode", source = "role.roleCode")
    UserResponse toResponse(UserEntity userEntity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "passwordHash", ignore = true) // хэшируется в сервисе
    @Mapping(target = "registeredAt", ignore = true) // ставится в сервисе (или @Builder.Default)
    @Mapping(target = "role", ignore = true)
    UserEntity toEntity(UserRequest request);
}