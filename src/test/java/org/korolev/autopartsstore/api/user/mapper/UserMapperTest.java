package org.korolev.autopartsstore.api.user.mapper;

import org.junit.jupiter.api.Test;
import org.korolev.autopartsstore.api.role.entity.RoleEntity;
import org.korolev.autopartsstore.api.user.dto.UserResponse;
import org.korolev.autopartsstore.api.user.entity.UserEntity;
import org.mapstruct.factory.Mappers;

import static org.assertj.core.api.Assertions.assertThat;

class UserMapperTest {

    private final UserMapper mapper = Mappers.getMapper(UserMapper.class);

    @Test
    void toResponse_shouldMapRoleCode() {
        RoleEntity role = RoleEntity.builder().roleCode("ROLE_ADMIN").build();
        UserEntity user = UserEntity.builder()
                .id(1L)
                .name("Иван")
                .surname("Королев")
                .email("test@test.ru")
                .phone("89999999999")
                .role(role)
                .build();

        UserResponse response = mapper.toResponse(user);

        assertThat(response.name()).isEqualTo("Иван");
        assertThat(response.email()).isEqualTo("test@test.ru");
        assertThat(response.roleCode()).isEqualTo("ROLE_ADMIN");
    }
}