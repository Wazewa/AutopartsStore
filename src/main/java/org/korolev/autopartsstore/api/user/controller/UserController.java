package org.korolev.autopartsstore.api.user.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.korolev.autopartsstore.api.user.dto.UserRequest;
import org.korolev.autopartsstore.api.user.dto.UserResponse;
import org.korolev.autopartsstore.api.user.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;


import java.util.List;

@RestController
@Tag(name = "Users", description = "Управление пользователями")
@RequestMapping("/api/users")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Все пользователи успешно отображены")
    })
    @Operation(summary = "Получить всех пользователей", description = "Возвращает список всех пользователей")
    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        return ResponseEntity.ok(userService.findAllUsers());
    }

    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Пользователь с заданным ID успешно отображен"),
            @ApiResponse(responseCode = "404", description = "Пользователь с заданным ID не найден")
    })
    @Operation(summary = "Получить пользователя по ID", description = "Возвращает пользователя по ID")
    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(
            @Parameter(description = "ID пользователя", example = "1") @PathVariable Long id) {
        return ResponseEntity.ok(userService.findUserById(id));
    }

    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Пользователь с заданным ID успешно удален"),
            @ApiResponse(responseCode = "404", description = "Пользователь с заданным ID не найден")
    })
    @Operation(summary = "Удалить пользователя по ID", description = "Удаляет пользователя по ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(
            @Parameter(description = "ID пользователя", example = "1") @PathVariable Long id) {
        userService.deleteUser(id);

        return ResponseEntity.noContent().build();
    }
}
