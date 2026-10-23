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
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
class OrderOwnerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Test
    @WithMockUser(username = "not_owner@example.com", roles = "CUSTOMER")
    void deleteOrder_whenNotOwner_returns403() throws Exception {
        RoleEntity customerRole = roleRepository.findByRoleCode("ROLE_CUSTOMER")
                .orElseThrow(() -> new RuntimeException("Role ROLE_CUSTOMER not found in test DB"));

        UserEntity owner = UserEntity.builder()
                .email("owner@example.com")
                .name("Owner").surname("Test")
                .passwordHash("hash")
                .role(customerRole)
                .build();
        userRepository.save(owner);

        OrderEntity order = OrderEntity.builder()
                .user(owner)
                .status(OrderStatus.PENDING)
                .payMethod(PayMethod.CARD)
                .orderDate(Instant.now())
                .build();
        orderRepository.save(order);

        mockMvc.perform(delete("/api/orders/" + order.getId()))
                .andExpect(status().isForbidden());
    }
}