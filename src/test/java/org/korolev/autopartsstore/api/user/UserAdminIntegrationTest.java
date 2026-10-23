package org.korolev.autopartsstore.api.user;

import org.junit.jupiter.api.Test;
import org.korolev.autopartsstore.api.role.entity.RoleEntity;
import org.korolev.autopartsstore.api.role.repository.RoleRepository;
import org.korolev.autopartsstore.api.user.entity.UserEntity;
import org.korolev.autopartsstore.api.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
class UserAdminIntegrationTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private UserRepository userRepository;
    @Autowired private RoleRepository roleRepository;

    @Test
    @WithMockUser(roles = "ADMIN")
    void adminCanListAndDeleteUsers() throws Exception {
        RoleEntity role = roleRepository.findByRoleCode("ROLE_CUSTOMER").orElseThrow();
        UserEntity user = UserEntity.builder()
                .email("delete_me@test.ru").name("Delete").surname("Me")
                .passwordHash("hash").role(role).build();
        userRepository.save(user);

        mockMvc.perform(get("/api/users?page=0&size=10"))
                .andExpect(status().isOk())
                .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath("$.content").isArray());

        mockMvc.perform(get("/api/users/" + user.getId()))
                .andExpect(status().isOk())
                .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath("$.email").value("delete_me@test.ru"));

        mockMvc.perform(delete("/api/users/" + user.getId()))
                .andExpect(status().isNoContent());
    }
}