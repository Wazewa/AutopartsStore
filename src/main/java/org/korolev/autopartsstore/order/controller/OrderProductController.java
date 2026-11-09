package org.korolev.autopartsstore.order.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.korolev.autopartsstore.order.dto.OrderProductRequest;
import org.korolev.autopartsstore.order.dto.OrderProductResponse;
import org.korolev.autopartsstore.order.service.OrderProductService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "OrderProducts", description = "Управление заказами товаров")
@AllArgsConstructor
@RequestMapping("/api/orders")
public class OrderProductController {

    private final OrderProductService orderProductService;

    @ApiResponses({
            @ApiResponse(responseCode = "200",
                    description = "Элементы заказа успешно отображены (может быть пустым)")
    })
    @Operation(summary = "Получить все элементы заказа",
            description = "Возвращает все элементы заказа с заданным ID")
    @GetMapping("/{orderId}/items")
    public List<OrderProductResponse> getOrderItems(@PathVariable Long orderId) {
        return orderProductService.findOrderItems(orderId);
    }

    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Товар успешно добавлен в заказ"),
            @ApiResponse(responseCode = "400", description = "Невалидные данные"),
            @ApiResponse(responseCode = "404", description = "Заказ с заданным ID не найден"),
            @ApiResponse(responseCode = "409", description = "Заказ нельзя изменить в текущем статусе")
    })
    @Operation(summary = "Добавить товар в заказ",
            description = "Добавляет товар в заказ. Заказ можно менять только в статусах PENDING или PROCESSING")
    @PostMapping("/{orderId}/items")
    @ResponseStatus(HttpStatus.CREATED)
    public OrderProductResponse addProductToOrder(
            @PathVariable Long orderId,
            @Valid @RequestBody OrderProductRequest orderProductRequest
    ) {
        return orderProductService.addProductToOrder(orderId, orderProductRequest);
    }

    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Количество товара успешно обновлено"),
            @ApiResponse(responseCode = "404", description = "Заказ или товар не найден"),
            @ApiResponse(responseCode = "409", description = "Заказ нельзя изменить в текущем статусе")
    })
    @Operation(summary = "Обновить количество товара в заказе",
            description = "Обновляет количество товара в заказе с заданным ID. " +
                    "Заказ можно менять только в статусах PENDING или PROCESSING")
    @PutMapping("/{orderId}/items/{productId}")
    public OrderProductResponse updateQuantity(
           @PathVariable Long orderId,
           @PathVariable Long productId,
           @RequestParam Integer quantity) {
        return orderProductService.updateQuantity(orderId, productId, quantity);
    }

    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Товар успешно удален из заказа"),
            @ApiResponse(responseCode = "404", description = "Заказ или товар не найден"),
            @ApiResponse(responseCode = "409", description = "Заказ нельзя изменить в текущем статусе")
    })
    @Operation(summary = "Удалить товар из заказа",
            description = "Удаляет конкретный товар из заказа с заданным ID. " +
                    "Товар из заказа можно удалять только в статусах PENDING или PROCESSING")
    @DeleteMapping("/{orderId}/items/{productId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeProductFromOrder(
            @PathVariable Long orderId,
            @PathVariable Long productId) {
        orderProductService.removeProductFromOrder(orderId, productId);
    }

    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Заказ успешно очищен"),
            @ApiResponse(responseCode = "404", description = "Заказ с заданным ID не найден"),
            @ApiResponse(responseCode = "409", description = "Заказ нельзя изменить в текущем статусе")
    })
    @Operation(summary = "Очистить заказ",
            description = "Очищает заказ с заданным ID. Заказ можно очищать только в статусах PENDING или PROCESSING")
    @DeleteMapping("/{orderId}/items")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void clearOrder(
            @PathVariable Long orderId) {
        orderProductService.clearOrder(orderId);
    }
}
