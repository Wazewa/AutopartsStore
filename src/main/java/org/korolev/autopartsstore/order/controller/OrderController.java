package org.korolev.autopartsstore.order.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.korolev.autopartsstore.common.dto.PageResponse;
import org.korolev.autopartsstore.common.factory.PageableFactory;
import org.korolev.autopartsstore.order.dto.OrderFilter;
import org.korolev.autopartsstore.order.dto.OrderRequest;
import org.korolev.autopartsstore.order.dto.OrderResponse;
import org.korolev.autopartsstore.order.dto.UpdateOrderStatusRequest;
import org.korolev.autopartsstore.order.entity.OrderStatus;
import org.korolev.autopartsstore.order.service.OrderService;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.Set;

@RestController
@Tag(name = "Orders", description = "Управление заказами")
@RequestMapping("/api/orders")
@AllArgsConstructor
public class OrderController {
    private static final Set<String> ALLOWED_SORT_FIELDS =
            Set.of("id", "orderDate", "status", "payMethod");

    private final OrderService orderService;

    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Все заказы успешно отображены (с пагинацией)"),
            @ApiResponse(responseCode = "403", description = "Доступ запрещен")
    })
    @Operation(summary = "Получить все заказы", description = "Возвращает все заказы (с пагинацией)")
    @GetMapping
    public PageResponse<OrderResponse> getAllOrders(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "25") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction,
            @RequestParam(required = false) OrderStatus status,
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            @RequestParam(required = false) Instant dateFrom,
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            @RequestParam(required = false) Instant dateTo
    ) {
        OrderFilter orderFilter = new OrderFilter(status, dateFrom, dateTo);

        return orderService.findAllOrders(PageableFactory.create(page, size,
                sortBy, direction, ALLOWED_SORT_FIELDS), orderFilter);
    }

    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Заказ с заданным ID успешно отображен"),
            @ApiResponse(responseCode = "403", description = "Доступ запрещен"),
            @ApiResponse(responseCode = "404", description = "Заказ с заданным ID не найден")
    })
    @Operation(summary = "Получить заказ по ID", description = "Возвращает заказ с заданным ID")
    @GetMapping("/{id}")
    public OrderResponse getOrderById(
            @PathVariable Long id) {
        return orderService.findOrderById(id);
    }

    @ApiResponses({
            @ApiResponse(responseCode = "200",
                    description = "Заказы клиента успешно отображены (может быть пустой список)")
    })
    @Operation(summary = "Получить мои заказы", description = "Возвращает заказы пользователя")
    @GetMapping("/me")
public PageResponse<OrderResponse> getMyOrders(
          @RequestParam(defaultValue = "0") int page,
          @RequestParam(defaultValue = "25") int size,
          @RequestParam(defaultValue = "id") String sortBy,
          @RequestParam(defaultValue = "asc") String direction)
    {
        Pageable pageable = PageableFactory.create(page, size, sortBy, direction, ALLOWED_SORT_FIELDS);
        return orderService.findMyOrders(pageable);
    }

    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Заказ успешно создан"),
            @ApiResponse(responseCode = "403", description = "Доступ запрещен"),
            @ApiResponse(responseCode = "404", description = "Клиент с заданным ID не найден"),
            @ApiResponse(responseCode = "400", description = "Невалидные данные")
    })
    @Operation(summary = "Создать заказ", description = "Создает заказ по заданному ID клиента")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderResponse createOrder(@Valid @RequestBody OrderRequest orderRequest) {
        return orderService.createOrder(orderRequest);
    }

    @ApiResponses({
            @ApiResponse(responseCode = "404", description = "Заказ с заданным ID не найден"),
            @ApiResponse(responseCode = "409", description = "Заказ невозможно перевести в заданное состояние"),
            @ApiResponse(responseCode = "400", description = "Невалидные данные")
    })
    @Operation(summary = "Обновить статус заказа", description = "Обновляет статус заказа по заданному ID заказа")
    @PatchMapping("/{id}/status")
    public OrderResponse updateOrderStatus(
            @PathVariable Long id,
            @RequestBody @Valid UpdateOrderStatusRequest request) {
        return orderService.updateOrderStatus(id, request.event());
    }

    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Заказ с заданным ID успешно удален"),
            @ApiResponse(responseCode = "404", description = "Заказ с заданным ID не найден"),
    })
    @Operation(summary = "Удалить заказ", description = "Удаляет заказ по заданному ID")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOrder(
            @PathVariable Long id) {
        orderService.deleteOrderById(id);
    }
}
