package org.korolev.autopartsstore.api.category.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.korolev.autopartsstore.api.category.dto.CategoryRequest;
import org.korolev.autopartsstore.api.category.dto.CategoryResponse;
import org.korolev.autopartsstore.api.category.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "Categories", description = "Управление категориями товаров")
@AllArgsConstructor
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryService categoryService;

    @ApiResponses(
            @ApiResponse(responseCode = "200", description = "Категории товаров успешно отображены")
    )
    @Operation(summary = "Получить все категории", description = "Возвращает список всех категорий товаров")
    @GetMapping
    public ResponseEntity<List<CategoryResponse>> getCategories() {
        return ResponseEntity.ok(categoryService.findAllCategories());
    }

    @ApiResponses({
            @ApiResponse(responseCode = "200",
                    description = "Категория товаров с заданным ID успешно отображена"),
            @ApiResponse(responseCode = "404",
                    description = "Категория товаров с заданным ID не найдена")
    })
    @Operation(summary = "Получить категорию по ID", description = "Возвращает категорию товаров по заданному ID")
    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponse> getCategoryById(
            @Parameter(description = "ID категории", example = "1") @PathVariable Long id) {
        return ResponseEntity.ok(categoryService.findCategoryById(id));
    }

    @ApiResponses({
            @ApiResponse(responseCode = "201",
                    description = "Категория успешно создана"),
            @ApiResponse(responseCode = "400",
                    description = "Невалидные данные"),
            @ApiResponse(responseCode = "409",
                    description = "Категория с заданным именем уже существует"),
    })
    @Operation(summary = "Создать категорию", description = "Создает категорию товаров")
    @PostMapping
    public ResponseEntity<CategoryResponse> createCategory(@Valid @RequestBody CategoryRequest categoryRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(categoryService.createCategory(categoryRequest));
    }

    @ApiResponses({
            @ApiResponse(responseCode = "200",
                    description = "Категория успешно обновлена"),
            @ApiResponse(responseCode = "400",
                    description = "Невалидные данные"),
            @ApiResponse(responseCode = "404",
                    description = "Категория товаров с заданным ID не найдена"),
            @ApiResponse(responseCode = "409",
                    description = "Категория с заданным именем уже существует")
    })
    @Operation(summary = "Обновить категорию", description = "Обновляет категорию товаров с заданным ID")
    @PutMapping("/{id}")
    public ResponseEntity<CategoryResponse> updateCategory(
            @Parameter(description = "ID категории", example = "1") @PathVariable Long id,
            @Valid @RequestBody CategoryRequest categoryRequest) {
        return ResponseEntity.ok(categoryService.updateCategory(id, categoryRequest));
    }

    @ApiResponses({
            @ApiResponse(responseCode = "204",
                    description = "Категория товаров с заданным ID успешно удалена"),
            @ApiResponse(responseCode = "404",
                    description = "Категория товаров с заданным ID не найдена"),
            @ApiResponse(responseCode = "409",
                    description = "Категория используется товарами и не может быть удалена")
    })
    @Operation(summary = "Удалить категорию по ID", description = "Удаляет категорию товаров по заданному ID. " +
            "Если категория используется товарами, то вернется 409 (Conflict)")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(
            @Parameter(description = "ID категории", example = "1") @PathVariable Long id) {
        categoryService.deleteCategory(id);

        return ResponseEntity.noContent().build();
    }
}
