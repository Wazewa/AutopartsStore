package org.korolev.automagazine.api.order.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.korolev.automagazine.api.order.dto.OrderProductRequest;
import org.korolev.automagazine.api.order.dto.OrderProductResponse;
import org.korolev.automagazine.api.order.service.OrderProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;

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
    public ResponseEntity<List<OrderProductResponse>> getOrderItems(
            @Parameter(description = "ID заказа", example = "1") @PathVariable Long orderId) {
        return ResponseEntity.ok(orderProductService.findOrderItems(orderId));
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
    public ResponseEntity<OrderProductResponse> addProductToOrder(
            @Parameter(description = "ID заказа", example = "1") @PathVariable Long orderId,
            @Valid @RequestBody OrderProductRequest orderProductRequest
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(orderProductService.addProductToOrder(
                orderId, orderProductRequest)
        );
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
    public ResponseEntity<OrderProductResponse> updateQuantity(
            @Parameter(description = "ID заказа", example = "1") @PathVariable Long orderId,
            @Parameter(description = "ID товара", example = "1") @PathVariable Long productId,
            @Parameter(description = "Количество товара", example = "2") @RequestParam Integer quantity) {
        return ResponseEntity.ok(orderProductService.updateQuantity(orderId, productId, quantity));
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
    public ResponseEntity<Void> removeProductFromOrder(
            @Parameter(description = "ID заказа", example = "1") @PathVariable Long orderId,
            @Parameter(description = "ID товара", example = "1") @PathVariable Long productId) {
        orderProductService.removeProductFromOrder(orderId, productId);

        return ResponseEntity.noContent().build();
    }

    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Заказ успешно очищен"),
            @ApiResponse(responseCode = "404", description = "Заказ с заданным ID не найден"),
            @ApiResponse(responseCode = "409", description = "Заказ нельзя изменить в текущем статусе")
    })
    @Operation(summary = "Очистить заказ",
            description = "Очищает заказ с заданным ID. Заказ можно очищать только в статусах PENDING или PROCESSING")
    @DeleteMapping("/{orderId}/items")
    public ResponseEntity<Void> clearOrder(
            @Parameter(description = "ID заказа", example = "1") @PathVariable Long orderId) {
        orderProductService.clearOrder(orderId);

        return ResponseEntity.noContent().build();
    }
}
