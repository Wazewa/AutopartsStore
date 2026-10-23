package org.korolev.autopartsstore.api.order.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.korolev.autopartsstore.api.dto.PageResponse;
import org.korolev.autopartsstore.api.factory.PageableFactory;
import org.korolev.autopartsstore.api.order.dto.OrderFilter;
import org.korolev.autopartsstore.api.order.dto.OrderRequest;
import org.korolev.autopartsstore.api.order.dto.OrderResponse;
import org.korolev.autopartsstore.api.order.dto.UpdateOrderStatusRequest;
import org.korolev.autopartsstore.api.order.entity.OrderStatus;
import org.korolev.autopartsstore.api.order.service.OrderService;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
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
    public ResponseEntity<PageResponse<OrderResponse>> getAllOrders(
            @Parameter(description = "Номер страницы", example = "0")
            @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Размер страницы", example = "25")
            @RequestParam(defaultValue = "25") int size,
            @Parameter(description = "Поле сортировки", example = "orderDate")
            @RequestParam(defaultValue = "id") String sortBy,
            @Parameter(description = "Направление сортировки", example = "desc")
            @RequestParam(defaultValue = "asc") String direction,
            @Parameter(description = "Фильтр статусов", example = "PROCESSING")
            @RequestParam(required = false) OrderStatus status,
            @Parameter(description = "Фильтр даты до")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            @RequestParam(required = false) Instant dateFrom,
            @Parameter(description = "Фильтр даты по")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            @RequestParam(required = false) Instant dateTo
    ) {
        OrderFilter orderFilter = new OrderFilter(status, dateFrom, dateTo);

        return ResponseEntity.ok(orderService.findAllOrders(PageableFactory.create(page, size,
                sortBy, direction, ALLOWED_SORT_FIELDS), orderFilter));
    }

    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Заказ с заданным ID успешно отображен"),
            @ApiResponse(responseCode = "403", description = "Доступ запрещен"),
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
    @Operation(summary = "Получить мои заказы", description = "Возвращает заказы пользователя")
    @GetMapping("/me")
public ResponseEntity<PageResponse<OrderResponse>> getMyOrders(
          @Parameter(description = "Номер страницы", example = "0")
          @RequestParam(defaultValue = "0") int page,
          @Parameter(description = "Размер страницы", example = "25")
          @RequestParam(defaultValue = "25") int size,
          @Parameter(description = "Поле сортировки", example = "orderDate")
          @RequestParam(defaultValue = "id") String sortBy,
          @Parameter(description = "Направление сортировки", example = "desc")
          @RequestParam(defaultValue = "asc") String direction)
    {
        Pageable pageable = PageableFactory.create(page, size, sortBy, direction, ALLOWED_SORT_FIELDS);
        return ResponseEntity.ok(orderService.findMyOrders(pageable));
    }

    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Заказ успешно создан"),
            @ApiResponse(responseCode = "403", description = "Доступ запрещен"),
            @ApiResponse(responseCode = "404", description = "Клиент с заданным ID не найден"),
            @ApiResponse(responseCode = "400", description = "Невалидные данные")
    })
    @Operation(summary = "Создать заказ", description = "Создает заказ по заданному ID клиента")
    @PostMapping
    public ResponseEntity<OrderResponse> createOrder(@Valid @RequestBody OrderRequest orderRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.createOrder(orderRequest));
    }

    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Заказ успешно создан"),
            @ApiResponse(responseCode = "404", description = "Заказ с заданным ID не найден"),
            @ApiResponse(responseCode = "409", description = "Заказ невозможно перевести в заданное состояние"),
            @ApiResponse(responseCode = "400", description = "Невалидные данные")
    })
    @Operation(summary = "Обновить статус заказа", description = "Обновляет статус заказа по заданному ID заказа")
    @PatchMapping("/{id}/status")
    public ResponseEntity<OrderResponse> updateOrderStatus(
            @Parameter(description = "ID заказа", example = "1") @PathVariable Long id,
            @RequestBody @Valid UpdateOrderStatusRequest request) {
        return ResponseEntity.ok(orderService.updateOrderStatus(id, request.event()));
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
