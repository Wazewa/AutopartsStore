package org.korolev.autopartsstore.category.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.korolev.autopartsstore.category.dto.CategoryRequest;
import org.korolev.autopartsstore.category.dto.CategoryResponse;
import org.korolev.autopartsstore.category.service.CategoryService;
import org.springframework.http.HttpStatus;
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
    public List<CategoryResponse> getCategories() {
        return categoryService.findAllCategories();
    }

    @ApiResponses({
            @ApiResponse(responseCode = "200",
                    description = "Категория товаров с заданным ID успешно отображена"),
            @ApiResponse(responseCode = "404",
                    description = "Категория товаров с заданным ID не найдена")
    })
    @Operation(summary = "Получить категорию по ID", description = "Возвращает категорию товаров по заданному ID")
    @GetMapping("/{id}")
    public CategoryResponse getCategoryById(
            @PathVariable Long id) {
        return categoryService.findCategoryById(id);
    }

    @ApiResponses({
            @ApiResponse(responseCode = "201",
                    description = "Категория успешно создана"),
            @ApiResponse(responseCode = "400",
                    description = "Невалидные данные"),
            @ApiResponse(responseCode = "409",
                    description = "Категория с заданным именем уже существует"),
            @ApiResponse(responseCode = "403", description = "Доступ запрещен")
    })
    @Operation(summary = "Создать категорию", description = "Создает категорию товаров")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryResponse createCategory(@Valid @RequestBody CategoryRequest categoryRequest) {
        return categoryService.createCategory(categoryRequest);
    }

    @ApiResponses({
            @ApiResponse(responseCode = "200",
                    description = "Категория успешно обновлена"),
            @ApiResponse(responseCode = "400",
                    description = "Невалидные данные"),
            @ApiResponse(responseCode = "404",
                    description = "Категория товаров с заданным ID не найдена"),
            @ApiResponse(responseCode = "409",
                    description = "Категория с заданным именем уже существует"),
            @ApiResponse(responseCode = "403", description = "Доступ запрещен")

    })
    @Operation(summary = "Обновить категорию", description = "Обновляет категорию товаров с заданным ID")
    @PutMapping("/{id}")
    public CategoryResponse updateCategory(
            @PathVariable Long id,
            @Valid @RequestBody CategoryRequest categoryRequest) {
        return categoryService.updateCategory(id, categoryRequest);
    }

    @ApiResponses({
            @ApiResponse(responseCode = "204",
                    description = "Категория товаров с заданным ID успешно удалена"),
            @ApiResponse(responseCode = "404",
                    description = "Категория товаров с заданным ID не найдена"),
            @ApiResponse(responseCode = "409",
                    description = "Категория используется товарами и не может быть удалена"),
            @ApiResponse(responseCode = "403", description = "Доступ запрещен")
    })
    @Operation(summary = "Удалить категорию по ID", description = "Удаляет категорию товаров по заданному ID. " +
            "Если категория используется товарами, то вернется 409 (Conflict)")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCategory(
            @PathVariable Long id) {
        categoryService.deleteCategory(id);
    }
}
