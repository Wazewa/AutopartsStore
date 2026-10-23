package org.korolev.autopartsstore.api.user.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.korolev.autopartsstore.api.dto.PageResponse;
import org.korolev.autopartsstore.api.user.dto.UserResponse;
import org.korolev.autopartsstore.api.user.service.UserService;
import org.korolev.autopartsstore.api.factory.PageableFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.Pageable;
import java.util.Set;

@RestController
@Tag(name = "Users", description = "Управление пользователями")
@RequestMapping("/api/users")
@AllArgsConstructor
public class UserController {
    private static final Set<String> ALLOWED_SORT_FIELDS =
            Set.of("id", "name", "surname", "email", "registeredAt");

    private final UserService userService;

    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Все пользователи успешно отображены (с пагинацией)"),
            @ApiResponse(responseCode = "403", description = "Доступ запрещен")
    })
    @Operation(summary = "Получить всех пользователей", description = "Возвращает список всех " +
            "пользователей (с пагинацией))")
    @GetMapping
    public ResponseEntity<PageResponse<UserResponse>> getAllUsers(
            @Parameter(description = "Номер страницы", example = "0")
            @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Размер страницы", example = "25")
            @RequestParam(defaultValue = "25") int size,
            @Parameter(description = "Поле сортировки", example = "registeredAt")
            @RequestParam(defaultValue = "id") String sortBy,
            @Parameter(description = "Направление сортировки", example = "desc")
            @RequestParam(defaultValue = "asc") String direction)
    {
        Pageable pageable = PageableFactory.create(page, size, sortBy, direction, ALLOWED_SORT_FIELDS);
        return ResponseEntity.ok(userService.findAllUsers(pageable));
    }

    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Пользователь с заданным ID успешно отображен"),
            @ApiResponse(responseCode = "403", description = "Доступ запрещен"),
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
