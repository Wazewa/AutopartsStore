package org.korolev.automagazine.api.cart.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.korolev.automagazine.api.cart.dto.CartProductRequest;
import org.korolev.automagazine.api.cart.dto.CartProductResponse;
import org.korolev.automagazine.api.cart.service.CartProductService;
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
import org.springframework.web.bind.annotation.RequestParam;


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
    public ResponseEntity<List<CartProductResponse>> getCartItems(
            @Parameter(description = "ID корзины", example = "1") @PathVariable Long cartId) {
        return ResponseEntity.ok(cartProductService.getCartItems(cartId));
    }

    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Товар успешно добавлен в корзину"),
            @ApiResponse(responseCode = "400", description = "Невалидные данные"),
            @ApiResponse(responseCode = "404", description = "Корзина с заданным ID не найдена")
    })
    @Operation(summary = "Добавить товар в корзину",
            description = "Добавляет элемент в корзину с заданным ID")
    @PostMapping ("/{cartId}/items")
    public ResponseEntity<CartProductResponse> addProductToCart(
            @Parameter(description = "ID корзины", example = "1") @PathVariable Long cartId,
                                                                 @Valid @RequestBody
                                                                 CartProductRequest cartProductRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(cartProductService.addProductToCart(
                cartId, cartProductRequest)
        );
    }

    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Количество товара успешно обновлено"),
            @ApiResponse(responseCode = "404", description = "Корзина или товар не найден")
    })
    @Operation(summary = "Обновить количество товара в корзине",
            description = "Обновляет количество товара в корзине с заданным ID")
    @PutMapping("/{cartId}/items/{productId}")
    public ResponseEntity<CartProductResponse> updateQuantity(
            @Parameter(description = "ID корзины", example = "1") @PathVariable Long cartId,
            @Parameter(description = "ID товара", example = "1") @PathVariable Long productId,
            @Parameter(description = "Количество товара", example = "2") @RequestParam Integer quantity) {
        return ResponseEntity.ok(cartProductService.updateQuantity(cartId, productId, quantity));
    }

    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Товар успешно удален из корзины"),
            @ApiResponse(responseCode = "404", description = "Корзина или товар не найден")
    })
    @Operation(summary = "Удалить товар из корзины",
            description = "Удаляет конкретный товар из корзины с заданным ID")
    @DeleteMapping("/{cartId}/items/{productId}")
    public ResponseEntity<Void> removeProductFromCart(
            @Parameter(description = "ID корзины", example = "1") @PathVariable Long cartId,
            @Parameter(description = "ID товара", example = "1") @PathVariable Long productId) {
        cartProductService.removeProductFromCart(cartId, productId);

        return ResponseEntity.noContent().build();
    }

    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Корзина успешно очищена"),
            @ApiResponse(responseCode = "404", description = "Корзина с заданным ID не найдена")
    })
    @Operation(summary = "Очистить корзину",
            description = "Очищает корзину с заданным ID")
    @DeleteMapping("/{cartId}/items")
    public ResponseEntity<Void> clearCart(
            @Parameter(description = "ID корзины", example = "1") @PathVariable Long cartId) {
        cartProductService.clearCart(cartId);

        return ResponseEntity.noContent().build();
    }
}
