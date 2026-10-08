package org.korolev.autopartsstore.api.product.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.korolev.autopartsstore.api.product.dto.ProductRequest;
import org.korolev.autopartsstore.api.product.dto.ProductResponse;
import org.korolev.autopartsstore.api.product.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@RestController
@Tag(name = "Products", description = "Управление товарами")
@AllArgsConstructor
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Все товары успешно отображены")
    })
    @Operation(summary = "Получить все товары", description = "Возвращает список всех товаров")
    @GetMapping
    public ResponseEntity<List<ProductResponse>> getProducts() {
        return ResponseEntity.ok(productService.findAllProducts());
    }

    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Товар с заданным ID успешно отображен"),
            @ApiResponse(responseCode = "404", description = "Товар с заданным ID не найден")
    })
    @Operation(summary = "Получить товар по ID", description = "Возвращает товар по ID")
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProductById(
            @Parameter(description = "ID товара", example = "1") @PathVariable Long id) {
        return ResponseEntity.ok(productService.findProductById(id));
    }

    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Товар успешно создан"),
            @ApiResponse(responseCode = "400", description = "Невалидные данные"),
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
            @ApiResponse(responseCode = "404", description = "Товар с заданным ID не найден"),
            @ApiResponse(responseCode = "409", description = "Товар с заданным артикулом уже есть")
    })
    @Operation(summary = "Обновить товар", description = "Обновляет товар по заданному ID")
    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> updateProduct(
            @Parameter(description = "ID товара", example = "1") @PathVariable Long id,
            @Valid @RequestBody ProductRequest productRequest) {
        return ResponseEntity.ok(productService.updateProduct(id, productRequest));
    }

    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Товар с заданным ID успешно удален"),
            @ApiResponse(responseCode = "404", description = "Товар с заданным ID не найден")
    })
    @Operation(summary = "Удалить товар по ID", description = "Удаляет товар по ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(
            @Parameter(description = "ID товара", example = "1") @PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}
