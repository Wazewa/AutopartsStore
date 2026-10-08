package org.korolev.autopartsstore.api.customer.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.korolev.autopartsstore.api.customer.dto.CustomerRequest;
import org.korolev.autopartsstore.api.customer.dto.CustomerResponse;
import org.korolev.autopartsstore.api.customer.service.CustomerService;
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


import java.util.List;

@RestController
@Tag(name = "Customers", description = "Управление клиентами")
@RequestMapping("/api/customers")
@AllArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Все клиенты успешно отображены")
    })
    @Operation(summary = "Получить всех клиентов", description = "Возвращает список всех клиентов")
    @GetMapping
    public ResponseEntity<List<CustomerResponse>> getAllCustomers() {
        return ResponseEntity.ok(customerService.findAllCustomers());
    }

    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Клиент с заданным ID успешно отображен"),
            @ApiResponse(responseCode = "404", description = "Клиент с заданным ID не найден")
    })
    @Operation(summary = "Получить клиента по ID", description = "Возвращает клиента по ID")
    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponse> getCustomerById(
            @Parameter(description = "ID клиента", example = "1") @PathVariable Long id) {
        return ResponseEntity.ok(customerService.findCustomerById(id));
    }

    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Клиент успешно создан"),
            @ApiResponse(responseCode = "400", description = "Невалидные данные"),
            @ApiResponse(responseCode = "409", description = "Клиент с заданным email уже есть")
    })
    @Operation(summary = "Создать клиента", description = "Создает клиента")
    @PostMapping
    public ResponseEntity<CustomerResponse> createCustomer(@Valid @RequestBody CustomerRequest customerRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(customerService.createCustomer(customerRequest));
    }

    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Клиент успешно обновлен"),
            @ApiResponse(responseCode = "400", description = "Невалидные данные"),
            @ApiResponse(responseCode = "404", description = "Клиент с заданным ID не найден"),
            @ApiResponse(responseCode = "409", description = "Клиент с заданным email уже есть")
    })
    @Operation(summary = "Обновить клиента", description = "Обновляет клиента по заданному ID")
    @PutMapping("/{id}")
    public ResponseEntity<CustomerResponse> updateCustomer(
            @Parameter(description = "ID клиента", example = "1") @PathVariable Long id,
            @Valid @RequestBody CustomerRequest customerRequest) {
        return ResponseEntity.ok(customerService.updateCustomer(id, customerRequest));
    }

    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Клиент с заданным ID успешно удален"),
            @ApiResponse(responseCode = "404", description = "Клиент с заданным ID не найден")
    })
    @Operation(summary = "Удалить клиента по ID", description = "Удаляет клиента по ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(
            @Parameter(description = "ID клиента", example = "1") @PathVariable Long id) {
        customerService.deleteCustomer(id);

        return ResponseEntity.noContent().build();
    }
}
