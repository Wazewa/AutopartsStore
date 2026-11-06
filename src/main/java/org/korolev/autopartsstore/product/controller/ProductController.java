package org.korolev.autopartsstore.product.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.korolev.autopartsstore.common.dto.PageResponse;
import org.korolev.autopartsstore.common.factory.PageableFactory;
import org.korolev.autopartsstore.product.dto.ProductFilter;
import org.korolev.autopartsstore.product.dto.ProductRequest;
import org.korolev.autopartsstore.product.dto.ProductResponse;
import org.korolev.autopartsstore.product.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Set;

@RestController
@Tag(name = "Products", description = "Управление товарами")
@AllArgsConstructor
@RequestMapping("/api/products")
public class ProductController {
    private static final Set<String> ALLOWED_SORT_FIELDS =
            Set.of("id", "name", "price", "article", "quantity", "brand");

    private final ProductService productService;

    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Все товары успешно отображены " +
                    "(с пагинацией, сортировкой и фильтрацией)")
    })
    @Operation(summary = "Получить все товары", description = "Возвращает список всех товаров " +
            "(с пагинацией, сортировкой и фильтрацией)")
    @GetMapping
    public ResponseEntity<PageResponse<ProductResponse>> getProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "25") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String brand,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) String article,
            @RequestParam(required = false) BigDecimal priceFrom,
            @RequestParam(required = false) BigDecimal priceTo,
            @RequestParam(required = false) Boolean inStock)
    {
        ProductFilter productFilter = new ProductFilter(name, brand, categoryId,
                article, priceFrom, priceTo, inStock);

        return ResponseEntity.ok(productService.findAllProducts(
                PageableFactory.create(page, size, sortBy, direction, ALLOWED_SORT_FIELDS), productFilter));
    }

    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Товар с заданным ID успешно отображен"),
            @ApiResponse(responseCode = "404", description = "Товар с заданным ID не найден")
    })
    @Operation(summary = "Получить товар по ID", description = "Возвращает товар по ID")
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProductById(
            @PathVariable Long id) {
        return ResponseEntity.ok(productService.findProductById(id));
    }

    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Товар успешно создан"),
            @ApiResponse(responseCode = "400", description = "Невалидные данные"),
            @ApiResponse(responseCode = "403", description = "Доступ запрещен"),
            @ApiResponse(responseCode = "409", description = "Товар с заданным артикулом уже есть")
    })
    @Operation(summary = "Создать товар", description = "Создает товар")
    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(@Valid @RequestBody ProductRequest productRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.createProduct(productRequest));
    }

    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Товар успешно обновлен"),
            @ApiResponse(responseCode = "400", description = "Невалидные данные"),
            @ApiResponse(responseCode = "403", description = "Доступ запрещен"),
            @ApiResponse(responseCode = "404", description = "Товар с заданным ID не найден"),
            @ApiResponse(responseCode = "409", description = "Товар с заданным артикулом уже есть")
    })
    @Operation(summary = "Обновить товар", description = "Обновляет товар по заданному ID")
    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> updateProduct(
            @PathVariable Long id,
            @Valid @RequestBody ProductRequest productRequest) {
        return ResponseEntity.ok(productService.updateProduct(id, productRequest));
    }

    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Товар с заданным ID успешно удален"),
            @ApiResponse(responseCode = "403", description = "Доступ запрещен"),
            @ApiResponse(responseCode = "404", description = "Товар с заданным ID не найден")
    })
    @Operation(summary = "Удалить товар по ID", description = "Удаляет товар по ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(
            @PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}
