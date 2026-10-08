package org.korolev.autopartsstore.api.order.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.korolev.autopartsstore.api.order.dto.OrderRequest;
import org.korolev.autopartsstore.api.order.dto.OrderResponse;
import org.korolev.autopartsstore.api.order.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@RestController
@Tag(name = "Orders", description = "Управление заказами")
@RequestMapping("/api/orders")
@AllArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Все заказы успешно отображены")
    })
    @Operation(summary = "Получить все заказы", description = "Возвращает все заказы")
    @GetMapping
    public ResponseEntity<List<OrderResponse>> getAllOrders() {
        return ResponseEntity.ok(orderService.findAllOrders());
    }

    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Заказ с заданным ID успешно отображен"),
            @ApiResponse(responseCode = "404", description = "Заказ с заданным ID не найден")
    })
    @Operation(summary = "Получить заказ по ID", description = "Возвращает заказ с заданным ID")
    @GetMapping("/{id}")
    public ResponseEntity<OrderResponse> getOrderById(
            @Parameter(description = "ID заказа", example = "1") @PathVariable Long id) {
        return ResponseEntity.ok(orderService.findOrderById(id));
    }

    @ApiResponses({
            @ApiResponse(responseCode = "200",
                    description = "Заказы клиента успешно отображены (может быть пустой список)")
    })
    @Operation(summary = "Получить заказ по ID клиента", description = "Возвращает заказ с заданным ID клиента")
    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<OrderResponse>> getOrdersByCustomerId(
            @Parameter(description = "ID клиента", example = "1") @PathVariable Long customerId) {
        return ResponseEntity.ok(orderService.findOrdersByCustomerId(customerId));
    }

    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Заказ успешно создан"),
            @ApiResponse(responseCode = "404", description = "Клиент с заданным ID не найден"),
            @ApiResponse(responseCode = "400", description = "Невалидные данные")
    })
    @Operation(summary = "Создать заказ", description = "Создает заказ по заданному ID клиента")
    @PostMapping
    public ResponseEntity<OrderResponse> createOrder(@Valid @RequestBody OrderRequest orderRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.createOrder(orderRequest));
    }

    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Заказ с заданным ID успешно удален"),
            @ApiResponse(responseCode = "404", description = "Заказ с заданным ID не найден"),
    })
    @Operation(summary = "Удалить заказ", description = "Удаляет заказ по заданному ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(
            @Parameter(description = "ID заказа", example = "1") @PathVariable Long id) {
        orderService.deleteOrderById(id);

        return ResponseEntity.noContent().build();
    }
}
