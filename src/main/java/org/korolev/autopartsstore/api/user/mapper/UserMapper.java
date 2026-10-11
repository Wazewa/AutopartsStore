package org.korolev.autopartsstore.api.user.mapper;

import org.korolev.autopartsstore.api.user.dto.UserResponse;
import org.korolev.autopartsstore.api.user.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "roleCode", source = "role.roleCode")
    UserResponse toResponse(UserEntity userEntity);
}