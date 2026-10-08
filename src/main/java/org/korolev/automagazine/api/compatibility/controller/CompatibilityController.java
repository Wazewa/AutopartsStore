package org.korolev.automagazine.api.compatibility.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.korolev.automagazine.api.compatibility.dto.CompatibilityRequest;
import org.korolev.automagazine.api.compatibility.dto.CompatibilityResponse;
import org.korolev.automagazine.api.compatibility.service.CompatibilityService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;

import java.util.List;

@RestController
@Tag(name = "Compatibilities", description = "Управление совместимостями товаров")
@AllArgsConstructor
@RequestMapping("/api/compatibilities")
public class CompatibilityController {

    private final CompatibilityService compatibilityService;

    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Совместимости успешно отображены")
    })
    @Operation(summary = "Получить все совместимости", description = "Возвращает все совместимости с товарами")
    @GetMapping
    public ResponseEntity<List<CompatibilityResponse>> getAllCompatibilities() {
        return ResponseEntity.ok(compatibilityService.findAllCompatibilities());
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
            @ApiResponse(responseCode = "404", description = "Товар с заданным ID не найден")
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
            @ApiResponse(responseCode = "404", description = "Товар или совместимость с заданным ID не найдена")
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
