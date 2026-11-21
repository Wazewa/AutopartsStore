package org.korolev.autopartsstore.api.product;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.korolev.autopartsstore.category.dto.CategoryRequest;
import org.korolev.autopartsstore.category.dto.CategoryResponse;
import org.korolev.autopartsstore.category.entity.CategoryEntity;
import org.korolev.autopartsstore.category.exception.CategoryNotFoundException;
import org.korolev.autopartsstore.category.repository.CategoryRepository;
import org.korolev.autopartsstore.category.service.CategoryFacade;
import org.korolev.autopartsstore.category.service.CategoryService;
import org.korolev.autopartsstore.product.dto.ProductRequest;
import org.korolev.autopartsstore.product.dto.ProductResponse;
import org.korolev.autopartsstore.product.entity.ProductEntity;
import org.korolev.autopartsstore.product.exception.ProductNotFoundException;
import org.korolev.autopartsstore.product.repository.ProductRepository;
import org.korolev.autopartsstore.product.service.ProductFacade;
import org.korolev.autopartsstore.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.function.Supplier;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
class CacheIntegrationTest {

    private static final String ADMIN = "admin@example.com";
    private static final Duration CACHE_WAIT = Duration.ofSeconds(2);

    @Autowired private ProductFacade productFacade;
    @Autowired private CategoryFacade categoryFacade;
    @Autowired private ProductService productService;
    @Autowired private CategoryService categoryService;
    @Autowired private ProductRepository productRepository;
    @Autowired private CategoryRepository categoryRepository;

    private final List<Long> createdProducts = new ArrayList<>();
    private final List<Long> createdCategories = new ArrayList<>();

    @AfterEach
    void cleanUp() {
        createdProducts.forEach(productRepository::deleteById);
        createdCategories.forEach(categoryRepository::deleteById);
        createdProducts.clear();
        createdCategories.clear();
    }

    private static void sleepQuietly(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }

    private static <T> T eventually(Supplier<T> supplier, Predicate<T> condition) {
        long deadline = System.nanoTime() + CACHE_WAIT.toNanos();
        T last = null;
        while (System.nanoTime() < deadline) {
            try {
                last = supplier.get();
            } catch (RuntimeException ex) {
                last = null;
            }
            if (last != null && condition.test(last)) {
                return last;
            }
            sleepQuietly(25);
        }
        return last;
    }

    private static void eventuallyThrows(Runnable action, Class<? extends RuntimeException> type) {
        long deadline = System.nanoTime() + CACHE_WAIT.toNanos();
        RuntimeException last = null;
        while (System.nanoTime() < deadline) {
            try {
                action.run();
            } catch (RuntimeException ex) {
                last = ex;
                if (type.isInstance(ex)) {
                    return;
                }
            }
            sleepQuietly(25);
        }
        if (last != null) {
            throw last;
        }
        throw new AssertionError("expected " + type.getSimpleName() + " but no exception was thrown");
    }

    private ProductEntity persistProduct(String name) {
        ProductEntity product = productRepository.save(ProductEntity.builder()
                .name(name)
                .price(new BigDecimal("900.00"))
                .quantity(3)
                .build());
        createdProducts.add(product.getId());
        return product;
    }

    private CategoryEntity persistCategory(String name, String description) {
        CategoryEntity category = categoryRepository.save(CategoryEntity.builder()
                .nameCategory(name)
                .descriptionCategory(description)
                .build());
        createdCategories.add(category.getId());
        return category;
    }

    @Test
    void getProduct_isServedFromCache() {
        Long id = persistProduct("Масло 5W-30").getId();

        productFacade.getProduct(id);
        productRepository.deleteById(id);

        ProductResponse cached = productFacade.getProduct(id);

        assertThat(cached.id()).isEqualTo(id);
        assertThat(cached.name()).isEqualTo("Масло 5W-30");
    }

    @Test
    void getCategory_isServedFromCache() {
        Long id = persistCategory("Кэш-категория", null).getId();

        categoryFacade.getCategory(id);
        categoryRepository.deleteById(id);

        CategoryResponse cached = categoryFacade.getCategory(id);

        assertThat(cached.id()).isEqualTo(id);
        assertThat(cached.nameCategory()).isEqualTo("Кэш-категория");
    }

    @Test
    void getCategory_returnsCachedFields() {
        CategoryEntity category = persistCategory("Инструменты", "Ручной инструмент");

        CategoryResponse response = categoryFacade.getCategory(category.getId());

        assertThat(response.id()).isEqualTo(category.getId());
        assertThat(response.nameCategory()).isEqualTo("Инструменты");
        assertThat(response.descriptionCategory()).isEqualTo("Ручной инструмент");
    }

    @Test
    @WithMockUser(username = ADMIN, roles = "ADMIN")
    void updateProduct_refreshesCacheEntry() {
        Long id = persistProduct("Старое имя").getId();
        productService.findProductById(id);

        productService.updateProduct(id, new ProductRequest(
                "Новое имя", new BigDecimal("1200.00"), "art-1", 7, null, "PEMCO"));

        ProductResponse cached = eventually(
                () -> productService.findProductById(id),
                value -> "Новое имя".equals(value.name()));

        assertThat(cached).isNotNull();
        assertThat(cached.name()).isEqualTo("Новое имя");
        assertThat(cached.price()).isEqualByComparingTo("1200.00");
    }

    @Test
    @WithMockUser(username = ADMIN, roles = "ADMIN")
    void updateCategory_refreshesCacheEntry() {
        Long id = persistCategory("Старая категория", null).getId();
        categoryService.findCategoryById(id);

        categoryService.updateCategory(id, new CategoryRequest("Новая категория", "Обновлено"));

        CategoryResponse cached = eventually(
                () -> categoryService.findCategoryById(id),
                value -> "Новая категория".equals(value.nameCategory()));

        assertThat(cached).isNotNull();
        assertThat(cached.nameCategory()).isEqualTo("Новая категория");
        assertThat(cached.descriptionCategory()).isEqualTo("Обновлено");
    }

    @Test
    @WithMockUser(username = ADMIN, roles = "ADMIN")
    void deleteProduct_evictsCacheEntry() {
        Long id = persistProduct("Удаляемый товар").getId();
        productService.findProductById(id);

        productService.deleteProduct(id);

        eventuallyThrows(() -> productService.findProductById(id), ProductNotFoundException.class);
    }

    @Test
    @WithMockUser(username = ADMIN, roles = "ADMIN")
    void deleteCategory_evictsCacheEntry() {
        Long id = persistCategory("Удаляемая категория", null).getId();
        categoryService.findCategoryById(id);

        categoryService.deleteCategory(id);

        eventuallyThrows(() -> categoryService.findCategoryById(id), CategoryNotFoundException.class);
    }
}
