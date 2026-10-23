package org.korolev.autopartsstore.api.compatibility.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.korolev.autopartsstore.api.compatibility.dto.CompatibilityFilter;
import org.korolev.autopartsstore.api.compatibility.dto.CompatibilityRequest;
import org.korolev.autopartsstore.api.compatibility.dto.CompatibilityResponse;
import org.korolev.autopartsstore.api.compatibility.service.CompatibilityService;
import org.korolev.autopartsstore.api.dto.PageResponse;
import org.korolev.autopartsstore.api.factory.PageableFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@Tag(name = "Compatibilities", description = "Управление совместимостями товаров")
@AllArgsConstructor
@RequestMapping("/api/compatibilities")
public class CompatibilityController {
    private static final Set<String> ALLOWED_SORT_FIELDS = Set.of(
            "id", "brand", "model", "yearStart", "yearEnd"
            );

    private final CompatibilityService compatibilityService;

    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Совместимости успешно отображены " +
                    "(с пагинацией, сортировкой и фильтрацией)")
    })
    @Operation(summary = "Получить все совместимости", description = "Возвращает все совместимости с " +
            "товарами (с пагинацией, сортировкой и фильтрацией)")
    @GetMapping
    public ResponseEntity<PageResponse<CompatibilityResponse>> getAllCompatibilities(
            @Parameter(description = "Номер страницы", example = "0")
            @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Размер страницы", example = "25")
            @RequestParam(defaultValue = "25") int size,
            @Parameter(description = "Поле сортировки", example = "yearStart")
            @RequestParam(defaultValue = "id") String sortBy,
            @Parameter(description = "Направление сортировки", example = "desc")
            @RequestParam(defaultValue = "asc") String direction,
            @Parameter(description = "Фильтр бренда", example = "BMW")
            @RequestParam(required = false) String brand,
            @Parameter(description = "Фильтр модели", example = "X5")
            @RequestParam(required = false) String model,
            @Parameter(description = "Год выпуска", example = "2002")
            @RequestParam(required = false) Integer year,
            @Parameter(description = "Фильтр ID продукта", example = "1")
            @RequestParam(required = false) Long productId)
    {
        CompatibilityFilter compatibilityFilter = new CompatibilityFilter(brand, model, year, productId);

        Pageable pageable = PageableFactory.create(page, size, sortBy, direction, ALLOWED_SORT_FIELDS);
        return ResponseEntity.ok(compatibilityService.findAllCompatibilities(pageable, compatibilityFilter));
    }

    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Совместимость по заданному ID успешно отображена"),
            @ApiResponse(responseCode = "404", description = "Совместимость по заданному ID не найдена")
    })
    @Operation(summary = "Получить совместимость по ID", description = "Возвращает совместимость по ID с товарами")
    @GetMapping("/{id}")
    public ResponseEntity<CompatibilityResponse> getCompatibilityById(
            @Parameter(description = "ID совместимости", example = "1") @PathVariable Long id) {
        return ResponseEntity.ok(compatibilityService.findCompatibilityById(id));
    }

    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Совместимость успешно создана"),
            @ApiResponse(responseCode = "400", description = "Невалидные данные (например, yearStart > yearEnd)"),
            @ApiResponse(responseCode = "404", description = "Товар с заданным ID не найден"),
            @ApiResponse(responseCode = "403", description = "Доступ запрещен")
    })
    @Operation(summary = "Создать совместимость", description = "Создает совместимость с товарами")
    @PostMapping
    public ResponseEntity<CompatibilityResponse> createCompatibility(
            @Valid @RequestBody CompatibilityRequest compatibilityRequest) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(compatibilityService.createCompatibility(compatibilityRequest));
    }

    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Совместимость успешно обновлена"),
            @ApiResponse(responseCode = "400", description = "Невалидные данные (например, yearStart > yearEnd)"),
            @ApiResponse(responseCode = "404", description = "Товар или совместимость с заданным ID не найдена"),
            @ApiResponse(responseCode = "403", description = "Доступ запрещен")
    })
    @Operation(summary = "Обновить совместимость по ID", description = "Обновляет совместимость с товарами")
    @PutMapping("/{id}")
    public ResponseEntity<CompatibilityResponse> updateCompatibility(
            @Parameter(description = "ID совместимости", example = "1") @PathVariable Long id,
            @Valid @RequestBody CompatibilityRequest compatibilityRequest) {
        return ResponseEntity.ok(compatibilityService.updateCompatibility(id, compatibilityRequest));

    }

    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Совместимость успешно удалена"),
            @ApiResponse(responseCode = "403", description = "Доступ запрещен"),
            @ApiResponse(responseCode = "404", description = "Совместимость с заданным ID не найдена")
    })
    @Operation(summary = "Удалить совместимость по ID", description = "Удаляет совместимость с товарами")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCompatibility(
            @Parameter(description = "ID совместимости", example = "1") @PathVariable Long id) {
        compatibilityService.deleteCompatibility(id);
        return ResponseEntity.noContent().build();
    }
}
