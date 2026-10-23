package org.korolev.autopartsstore.api.order;

import org.junit.jupiter.api.Test;
import org.korolev.autopartsstore.api.order.entity.OrderEntity;
import org.korolev.autopartsstore.api.order.entity.OrderStatus;
import org.korolev.autopartsstore.api.order.entity.PayMethod;
import org.korolev.autopartsstore.api.order.repository.OrderRepository;
import org.korolev.autopartsstore.api.role.entity.RoleEntity;
import org.korolev.autopartsstore.api.role.repository.RoleRepository;
import org.korolev.autopartsstore.api.user.entity.UserEntity;
import org.korolev.autopartsstore.api.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
class OrderFullLifecycleIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    @Test
    @WithMockUser(username = "customer@example.com", roles = "CUSTOMER")
    void createOrder_andChangeStatus_asAdmin_success() throws Exception {
        // 1. Подготовка пользователя
        RoleEntity customerRole = roleRepository.findByRoleCode("ROLE_CUSTOMER").orElseThrow();
        UserEntity user = UserEntity.builder()
                .email("customer@example.com").name("Cust").surname("Omer")
                .passwordHash("hash").role(customerRole).build();
        userRepository.save(user);

        mockMvc.perform(post("/api/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"payMethod\": \"CARD\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.status").value("PENDING"));

        OrderEntity order = orderRepository.findAll().stream()
                .filter(o -> o.getUser().getEmail().equals("customer@example.com"))
                .findFirst().orElseThrow();
    }

    @Test
    @WithMockUser(username = "admin@example.com", roles = "ADMIN")
    void changeStatus_asAdmin_success() throws Exception {
        RoleEntity role = roleRepository.findByRoleCode("ROLE_CUSTOMER").orElseThrow();
        UserEntity user = UserEntity.builder()
                .email("admin@example.com").name("Adm").surname("In").passwordHash("hash").role(role).build();
        userRepository.save(user);

        OrderEntity order = OrderEntity.builder()
                .user(user).status(OrderStatus.PENDING).payMethod(PayMethod.CARD)
                .orderDate(Instant.now()).build();
        orderRepository.save(order);

        mockMvc.perform(patch("/api/orders/" + order.getId() + "/status")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"event\": \"START_PROCESSING\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("PROCESSING"));

        mockMvc.perform(patch("/api/orders/" + order.getId() + "/status")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"event\": \"START_PROCESSING\"}"))
                .andExpect(status().isConflict());
    }
}