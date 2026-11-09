package org.korolev.autopartsstore.user.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.korolev.autopartsstore.common.dto.PageResponse;
import org.korolev.autopartsstore.user.dto.UserResponse;
import org.korolev.autopartsstore.user.service.UserService;
import org.korolev.autopartsstore.common.factory.PageableFactory;
import org.springframework.http.HttpStatus;
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
    public PageResponse<UserResponse> getAllUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "25") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction)
    {
        Pageable pageable = PageableFactory.create(page, size, sortBy, direction, ALLOWED_SORT_FIELDS);
        return userService.findAllUsers(pageable);
    }

    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Пользователь с заданным ID успешно отображен"),
            @ApiResponse(responseCode = "403", description = "Доступ запрещен"),
            @ApiResponse(responseCode = "404", description = "Пользователь с заданным ID не найден")
    })
    @Operation(summary = "Получить пользователя по ID", description = "Возвращает пользователя по ID")
    @GetMapping("/{id}")
    public UserResponse getUserById(
            @PathVariable Long id) {
        return userService.findUserById(id);
    }

    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Пользователь с заданным ID успешно удален"),
            @ApiResponse(responseCode = "404", description = "Пользователь с заданным ID не найден")
    })
    @Operation(summary = "Удалить пользователя по ID", description = "Удаляет пользователя по ID")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(
            @PathVariable Long id) {
        userService.deleteUser(id);
    }
}
