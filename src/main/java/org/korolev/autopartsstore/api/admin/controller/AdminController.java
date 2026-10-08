package org.korolev.autopartsstore.api.admin.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.korolev.autopartsstore.api.admin.dto.AdminRequest;
import org.korolev.autopartsstore.api.admin.dto.AdminResponse;
import org.korolev.autopartsstore.api.admin.service.AdminService;
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
@Tag(name = "Admins", description = "Управление админами")
@AllArgsConstructor
@RequestMapping("/api/admins")
public class AdminController {

    private final AdminService adminService;

    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Все найденные админы отображены"),
    })
    @Operation(summary = "Получить всех админов", description = "Возвращает список всех админов")
    @GetMapping
    public ResponseEntity<List<AdminResponse>> getAdmins() {
        return ResponseEntity.ok(adminService.findAllAdmins());
    }

    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Админ с заданным ID успешно найден"),
            @ApiResponse(responseCode = "404", description = "Админ с заданным ID не найден")
    })
    @Operation(summary = "Получить админа по ID", description = "Возвращает админа по его ID")
    @GetMapping("/{id}")
    public ResponseEntity<AdminResponse> getAdminById(
            @Parameter(description = "ID админа", example = "1") @PathVariable Long id) {
        return ResponseEntity.ok(adminService.findAdminById(id));
    }

    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Админ успешно создан"),
            @ApiResponse(responseCode = "400", description = "Невалидные данные"),
            @ApiResponse(responseCode = "409", description = "Админ с заданным email уже существует")
    })
    @Operation(summary = "Создать админа", description = "Создает админа и возвращает его")
    @PostMapping
    public ResponseEntity<AdminResponse> createAdmin(@Valid @RequestBody AdminRequest adminRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(adminService.createAdmin(adminRequest));
    }

    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Админ успешно обновлен"),
            @ApiResponse(responseCode = "400", description = "Невалидные данные"),
            @ApiResponse(responseCode = "404", description = "Админ с заданным ID не найден"),
            @ApiResponse(responseCode = "409", description = "Админ с заданным email уже существует")
    })
    @Operation(summary = "Обновить админа",
            description = "Обновляет админу данные по его ID и возвращает его")
    @PutMapping("/{id}")
    public ResponseEntity<AdminResponse> updateAdmin(
            @Parameter(description = "ID админа", example = "1") @PathVariable Long id,
            @Valid @RequestBody AdminRequest adminRequest) {
        return ResponseEntity.ok(adminService.updateAdmin(id, adminRequest));
    }

    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Админ успешно удален"),
            @ApiResponse(responseCode = "404", description = "Админ с заданным ID не найден")
    })
    @Operation(summary = "Удалить админа",
            description = "Удаляет админа по его ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAdmin(
            @Parameter(description = "ID админа", example = "1") @PathVariable Long id) {
        adminService.deleteAdmin(id);
        return ResponseEntity.noContent().build();
    }

}
