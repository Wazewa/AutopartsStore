package org.korolev.autopartsstore.api.cart.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.korolev.autopartsstore.api.cart.dto.CartResponse;
import org.korolev.autopartsstore.api.cart.service.CartService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@RestController
@Tag(name = "Carts", description = "Управление пользовательскими корзинами")
@AllArgsConstructor
@RequestMapping("/api/carts")
public class CartController {

    private final CartService cartService;

    @ApiResponses(
            @ApiResponse(responseCode = "200", description = "Все пользовательские корзины были отображены")
    )
    @Operation(summary = "Получить все корзины", description = "Возвращает список всех пользовательских корзин")
    @GetMapping
    public ResponseEntity<List<CartResponse>> getAllCarts() {
        return ResponseEntity.ok(cartService.findAllCarts());
    }

    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Корзина с заданным ID успешно найдена"),
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
            @ApiResponse(responseCode = "200", description = "Корзина пользователя с заданным ID успешно найдена"),
            @ApiResponse(responseCode = "404", description = "Корзина с заданным ID пользователя не найдена")
    })
    @Operation(summary = "Получить корзину по ID пользователя",
            description = "Возвращает корзину по заданному ID пользователя")
    @GetMapping("/customer/{customerId}")
    public ResponseEntity<CartResponse> getCartByCustomerId(
            @Parameter(description = "ID пользователя", example = "1") @PathVariable Long customerId) {
        return ResponseEntity.ok(cartService.findCartByCustomerId(customerId));
    }

    @ApiResponses({
            @ApiResponse(responseCode = "201",
                    description = "Корзина для пользователя с заданным ID успешно создана"),
            @ApiResponse(responseCode = "404", description = "Пользователь с заданным ID не найден")
    })
    @Operation(summary = "Создать корзину для пользователя ",
            description = "Создает корзину для пользователя с заданным ID")
    @PostMapping("/customer/{customerId}")
    public ResponseEntity<CartResponse> getOrCreateCart(
            @Parameter(description = "ID пользователя", example = "1") @PathVariable Long customerId) {
        return ResponseEntity.status(HttpStatus.CREATED).body(cartService.getOrCreateCart(customerId));
    }

    @ApiResponses({
            @ApiResponse(responseCode = "204",
                    description = "Корзина для пользователя с заданным ID успешно удалена"),
            @ApiResponse(responseCode = "404", description = "Корзина с заданным ID не найдена")
    })
    @Operation(summary = "Удалить корзину",
            description = "Удаляет корзину по ее ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCart(
            @Parameter(description = "ID корзины", example = "1") @PathVariable Long id) {
        cartService.deleteCart(id);

        return ResponseEntity.noContent().build();
    }
}
