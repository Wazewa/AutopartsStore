package org.korolev.autopartsstore.cart.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.korolev.autopartsstore.cart.dto.CartProductRequest;
import org.korolev.autopartsstore.cart.dto.CartProductResponse;
import org.korolev.autopartsstore.cart.service.CartProductService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@Tag(name = "CartProducts", description = "Управление корзиной товаров")
@RequestMapping("/api/carts")
@AllArgsConstructor
public class CartProductController {

    private final CartProductService cartProductService;

    @ApiResponses({
            @ApiResponse(responseCode = "200",
                    description = "Элементы корзины успешно отображены (может быть пустым)")
    })
    @Operation(summary = "Получить все элементы корзины",
            description = "Возвращает все элементы корзины с заданным ID")
    @GetMapping("/{cartId}/items")
    public List<CartProductResponse> getCartItems(
            @PathVariable Long cartId) {
        return cartProductService.getCartItems(cartId);
    }

    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Товар успешно добавлен в корзину"),
            @ApiResponse(responseCode = "400", description = "Невалидные данные"),
            @ApiResponse(responseCode = "404", description = "Корзина с заданным ID не найдена")
    })
    @Operation(summary = "Добавить товар в корзину",
            description = "Добавляет элемент в корзину с заданным ID")
    @PostMapping ("/{cartId}/items")
    @ResponseStatus(HttpStatus.CREATED)
    public CartProductResponse addProductToCart(
           @PathVariable Long cartId,
           @Valid @RequestBody
           CartProductRequest cartProductRequest) {
        return cartProductService.addProductToCart(
                cartId, cartProductRequest);
    }

    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Количество товара успешно обновлено"),
            @ApiResponse(responseCode = "404", description = "Корзина или товар не найден")
    })
    @Operation(summary = "Обновить количество товара в корзине",
            description = "Обновляет количество товара в корзине с заданным ID")
    @PutMapping("/{cartId}/items/{productId}")
    public CartProductResponse updateQuantity(
            @PathVariable Long cartId,
            @PathVariable Long productId,
            @RequestParam Integer quantity) {
        return cartProductService.updateQuantity(cartId, productId, quantity);
    }

    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Товар успешно удален из корзины"),
            @ApiResponse(responseCode = "404", description = "Корзина или товар не найден")
    })
    @Operation(summary = "Удалить товар из корзины",
            description = "Удаляет конкретный товар из корзины с заданным ID")
    @DeleteMapping("/{cartId}/items/{productId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeProductFromCart(
            @PathVariable Long cartId,
            @PathVariable Long productId) {
        cartProductService.removeProductFromCart(cartId, productId);
    }

    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Корзина успешно очищена"),
            @ApiResponse(responseCode = "404", description = "Корзина с заданным ID не найдена")
    })
    @Operation(summary = "Очистить корзину",
            description = "Очищает корзину с заданным ID")
    @DeleteMapping("/{cartId}/items")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void clearCart(
            @PathVariable Long cartId) {
        cartProductService.clearCart(cartId);
    }
}
