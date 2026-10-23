package org.korolev.autopartsstore.api;

import org.junit.jupiter.api.Test;
import org.korolev.autopartsstore.api.category.dto.CategoryRequest;
import org.korolev.autopartsstore.api.category.entity.CategoryEntity;
import org.korolev.autopartsstore.api.category.repository.CategoryRepository;
import org.korolev.autopartsstore.api.compatibility.dto.CompatibilityRequest;
import org.korolev.autopartsstore.api.product.entity.ProductEntity;
import org.korolev.autopartsstore.api.product.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
class CrudIntegrationTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private CategoryRepository categoryRepository;
    @Autowired private ProductRepository productRepository;

    private final tools.jackson.databind.ObjectMapper objectMapper = new tools.jackson.databind.ObjectMapper();

    @Test
    @WithMockUser(roles = "ADMIN")
    void categoryAndCompatibility_fullLifecycle() throws Exception {
        CategoryRequest catRequest = new CategoryRequest("Тестовая категория: Масла", "Моторные масла");

        mockMvc.perform(post("/api/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(catRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Тестовая категория: Масла"));

        CategoryEntity category = categoryRepository.findAll().stream()
                .filter(c -> c.getName().equals("Тестовая категория: Масла")).findFirst().orElseThrow();

        CategoryRequest updateCatRequest = new CategoryRequest("Автомасла", "Обновлено");
        mockMvc.perform(put("/api/categories/" + category.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateCatRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Автомасла"));

        ProductEntity product = ProductEntity.builder()
                .name("Масло 10W-40").price(new BigDecimal("1500")).quantity(5).build();
        productRepository.save(product);

        CompatibilityRequest compRequest = new CompatibilityRequest(
                product.getId(), "Lada", "Vesta", 2015, 2023
        );
        mockMvc.perform(post("/api/compatibilities")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(compRequest)))
                .andExpect(status().isCreated());

        mockMvc.perform(get("/api/compatibilities?year=2020"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].brand").value("Lada"));

        mockMvc.perform(delete("/api/categories/" + category.getId()))
                .andExpect(status().isNoContent());
    }
}