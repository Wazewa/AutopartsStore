package org.korolev.autopartsstore.api.product.mapper;

import org.junit.jupiter.api.Test;
import org.korolev.autopartsstore.api.category.entity.CategoryEntity;
import org.korolev.autopartsstore.api.product.dto.ProductRequest;
import org.korolev.autopartsstore.api.product.dto.ProductResponse;
import org.korolev.autopartsstore.api.product.entity.ProductEntity;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class ProductMapperTest {

    private final ProductMapper mapper = Mappers.getMapper(ProductMapper.class);

    @Test
    void toResponse_shouldMapAllFields() {
        ProductEntity entity = ProductEntity.builder()
                .id(1L)
                .name("Масло")
                .price(new BigDecimal("1000.00"))
                .article("12345")
                .quantity(10)
                .imageUrl("http://img")
                .brand("PEMCO")
                .category(CategoryEntity.builder().id(5L).build())
                .build();

        ProductResponse response = mapper.toResponse(entity);

        assertThat(response.id()).isEqualTo(1L);
        assertThat(response.name()).isEqualTo("Масло");
        assertThat(response.price()).isEqualByComparingTo("1000.00");
        assertThat(response.article()).isEqualTo("12345");
        assertThat(response.quantity()).isEqualTo(10);
        assertThat(response.imageUrl()).isEqualTo("http://img");
        assertThat(response.brand()).isEqualTo("PEMCO");
    }

    @Test
    void toEntity_shouldMapRequestFields() {
        ProductRequest request = new ProductRequest(
                "Фильтр", new BigDecimal("500"), "арт-1", 5, "url", "MANNOL"
        );

        ProductEntity entity = mapper.toEntity(request);

        assertThat(entity.getId()).isNull();
        assertThat(entity.getName()).isEqualTo("Фильтр");
        assertThat(entity.getPrice()).isEqualByComparingTo("500");
        assertThat(entity.getArticle()).isEqualTo("арт-1");
        assertThat(entity.getQuantity()).isEqualTo(5);
        assertThat(entity.getImageUrl()).isEqualTo("url");
        assertThat(entity.getBrand()).isEqualTo("MANNOL");
        assertThat(entity.getCategory()).isNull();
    }

    @Test
    void updateEntity_shouldUpdateOnlyNonNullFields() {
        ProductEntity entity = ProductEntity.builder()
                .name("Старое имя")
                .price(new BigDecimal("100"))
                .build();
        ProductRequest request = new ProductRequest(
                "Новое имя", new BigDecimal("200"), null, null, null, null
        );

        mapper.updateEntity(request, entity);

        assertThat(entity.getName()).isEqualTo("Новое имя");
        assertThat(entity.getPrice()).isEqualByComparingTo("200");
    }
}