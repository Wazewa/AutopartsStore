package org.korolev.autopartsstore.api.cart;

import org.junit.jupiter.api.Test;
import org.korolev.autopartsstore.api.cart.entity.CartEntity;
import org.korolev.autopartsstore.api.product.entity.ProductEntity;
import org.korolev.autopartsstore.api.product.repository.ProductRepository;
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
import tools.jackson.databind.json.JsonMapper;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
class CartIntegrationTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private ProductRepository productRepository;

    @Test
    @WithMockUser(username = "cart_user@example.com", roles = "CUSTOMER")
    void fullCartLifecycle_success() throws Exception {
        RoleEntity role = roleRepository.findByRoleCode("ROLE_CUSTOMER").orElseThrow();
        UserEntity user = UserEntity.builder()
                .email("cart_user@example.com").name("Cart").surname("User")
                .passwordHash("hash").role(role).build();
        userRepository.save(user);

        ProductEntity product = ProductEntity.builder()
                .name("Тестовый товар").price(new BigDecimal("1000")).quantity(10).build();
        productRepository.save(product);

        mockMvc.perform(post("/api/carts/user/me"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists());

        String cartResponse = mockMvc.perform(get("/api/carts/user/me"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        Long cartId = JsonMapper.builder().build()
                .readTree(cartResponse).get("id").asLong();

        mockMvc.perform(post("/api/carts/" + cartId + "/items")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"productId\": " + product.getId() + ", \"quantity\": 2}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.quantity").value(2));

        mockMvc.perform(get("/api/carts/" + cartId + "/items"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].productId").value(product.getId()))
                .andExpect(jsonPath("$[0].quantity").value(2));

        mockMvc.perform(put("/api/carts/" + cartId + "/items/" + product.getId() + "?quantity=5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.quantity").value(5));

        mockMvc.perform(delete("/api/carts/" + cartId + "/items/" + product.getId()))
                .andExpect(status().isNoContent());

        mockMvc.perform(delete("/api/carts/user/me"))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser(username = "other_cart_user@example.com", roles = "CUSTOMER")
    void addProductToForeignCart_returns403() throws Exception {
        RoleEntity role = roleRepository.findByRoleCode("ROLE_CUSTOMER").orElseThrow();
        UserEntity owner = UserEntity.builder()
                .email("owner_cart@example.com").name("Owner").surname("Cart")
                .passwordHash("hash").role(role).build();
        userRepository.save(owner);

        mockMvc.perform(post("/api/carts/user/me").with(req -> {
            req.setUserPrincipal(() -> "owner_cart@example.com");
            return req;
        }));
    }
}