package org.korolev.autopartsstore.api.cart.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.korolev.autopartsstore.api.cart.dto.CartResponse;
import org.korolev.autopartsstore.api.cart.service.CartService;
import org.korolev.autopartsstore.api.dto.PageResponse;
import org.korolev.autopartsstore.api.factory.PageableFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.Set;

@RestController
@Tag(name = "Carts", description = "Управление пользовательскими корзинами")
@AllArgsConstructor
@RequestMapping("/api/carts")
public class CartController {
    private static final Set<String> ALLOWED_SORT_FIELDS = Set.of(
            "id", "createdDate", "modifiedDate"
    );

    private final CartService cartService;

    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Все пользовательские корзины были отображены"),
            @ApiResponse(responseCode = "403", description = "Доступ запрещен")
    })
    @Operation(summary = "Получить все корзины", description = "Возвращает список всех пользовательских корзин")
    @GetMapping
    public ResponseEntity<PageResponse<CartResponse>> getAllCarts(
            @Parameter(description = "Номер страницы", example = "0")
            @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Размер страницы", example = "25")
            @RequestParam(defaultValue = "25") int size,
            @Parameter(description = "Поле сортировки", example = "createdDate")
            @RequestParam(defaultValue = "id") String sortBy,
            @Parameter(description = "Направление сортировки", example = "desc")
            @RequestParam(defaultValue = "asc") String direction)
    {
        Pageable pageable = PageableFactory.create(page, size, sortBy, direction, ALLOWED_SORT_FIELDS);
        return ResponseEntity.ok(cartService.findAllCarts(pageable));
    }

    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Корзина с заданным ID успешно найдена"),
            @ApiResponse(responseCode = "403", description = "Доступ запрещен"),
            @ApiResponse(responseCode = "404", description = "Корзина с заданным ID не найдена")
    })
    @Operation(summary = "Получить корзину по ID",
            description = "Возвращает пользовательскую корзину по заданному ID")
    @GetMapping("/{id}")
    public ResponseEntity<CartResponse> getCartById(
            @Parameter(description = "ID корзины", example = "1") @PathVariable Long id) {
        return ResponseEntity.ok(cartService.findCartById(id));
    }

    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Корзина пользователя успешно найдена"),
            @ApiResponse(responseCode = "404", description = "Корзина пользователя не найдена")
    })
    @Operation(summary = "Получить корзину пользователя",
            description = "Возвращает корзину пользователя")
    @GetMapping("/user/me")
    public ResponseEntity<CartResponse> getCartByUserId() {
        return ResponseEntity.ok(cartService.findMyCart());
    }

    @ApiResponses({
            @ApiResponse(responseCode = "201",
                    description = "Корзина пользователя успешно создана")
    })
    @Operation(summary = "Создать корзину пользователю",
            description = "Создает корзину для пользователя")
    @PostMapping("/user/me")
    public ResponseEntity<CartResponse> getOrCreateCart() {
        return ResponseEntity.status(HttpStatus.CREATED).body(cartService.getOrCreateCart());
    }

    @ApiResponses({
            @ApiResponse(responseCode = "204",
                    description = "Корзина для пользователя с заданным ID успешно удалена"),
            @ApiResponse(responseCode = "404", description = "Корзина с заданным ID не найдена")
    })
    @Operation(summary = "Удалить корзину",
            description = "Удаляет корзину пользователя")
    @DeleteMapping("/user/me")
    public ResponseEntity<Void> deleteCart() {
        cartService.deleteCart();

        return ResponseEntity.noContent().build();
    }
}
