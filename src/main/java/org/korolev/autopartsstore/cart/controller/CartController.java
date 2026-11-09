package org.korolev.autopartsstore.cart.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.korolev.autopartsstore.cart.dto.CartResponse;
import org.korolev.autopartsstore.cart.service.CartService;
import org.korolev.autopartsstore.common.dto.PageResponse;
import org.korolev.autopartsstore.common.factory.PageableFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
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
    public PageResponse<CartResponse> getAllCarts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "25") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction)
    {
        Pageable pageable = PageableFactory.create(page, size, sortBy, direction, ALLOWED_SORT_FIELDS);
        return cartService.findAllCarts(pageable);
    }

    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Корзина с заданным ID успешно найдена"),
            @ApiResponse(responseCode = "403", description = "Доступ запрещен"),
            @ApiResponse(responseCode = "404", description = "Корзина с заданным ID не найдена")
    })
    @Operation(summary = "Получить корзину по ID",
            description = "Возвращает пользовательскую корзину по заданному ID")
    @GetMapping("/{id}")
    public CartResponse getCartById(
            @PathVariable Long id) {
        return cartService.findCartById(id);
    }

    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Корзина пользователя успешно найдена"),
            @ApiResponse(responseCode = "404", description = "Корзина пользователя не найдена")
    })
    @Operation(summary = "Получить корзину пользователя",
            description = "Возвращает корзину пользователя")
    @GetMapping("/user/me")
    public CartResponse getCartByUserId() {
        return cartService.findMyCart();
    }

    @ApiResponses({
            @ApiResponse(responseCode = "201",
                    description = "Корзина пользователя успешно создана")
    })
    @Operation(summary = "Создать корзину пользователю",
            description = "Создает корзину для пользователя")
    @PostMapping("/user/me")
    @ResponseStatus(HttpStatus.CREATED)
    public CartResponse getOrCreateCart() {
        return cartService.getOrCreateCart();
    }

    @ApiResponses({
            @ApiResponse(responseCode = "204",
                    description = "Корзина для пользователя с заданным ID успешно удалена"),
            @ApiResponse(responseCode = "404", description = "Корзина с заданным ID не найдена")
    })
    @Operation(summary = "Удалить корзину",
            description = "Удаляет корзину пользователя")
    @DeleteMapping("/user/me")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCart() {
        cartService.deleteCart();
    }
}
