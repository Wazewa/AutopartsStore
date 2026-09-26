package org.korolev.automagazine.api.controllers;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.korolev.automagazine.api.dto.OrderProductRequest;
import org.korolev.automagazine.api.dto.OrderProductResponse;
import org.korolev.automagazine.api.services.OrderProductService;
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
@AllArgsConstructor
@RequestMapping("/api/orders")
public class OrderProductController {

    private final OrderProductService orderProductService;

    @GetMapping("/{orderId}/items")
    public ResponseEntity<List<OrderProductResponse>> getOrderItems(@PathVariable Long orderId) {
        return ResponseEntity.ok(orderProductService.findOrderItems(orderId));
    }

    @PostMapping("/{orderId}/items")
    public ResponseEntity<OrderProductResponse> addProductToOrder(
            @PathVariable Long orderId, @Valid @RequestBody OrderProductRequest orderProductRequest
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(orderProductService.addProductToOrder(
                orderId, orderProductRequest)
        );
    }

    @PutMapping("/{orderId}/items/{productId}")
    public ResponseEntity<OrderProductResponse> updateQuantity(
            @PathVariable Long orderId,
            @PathVariable Long productId,
            @RequestParam Integer quantity) {
        return ResponseEntity.ok(orderProductService.updateQuantity(orderId, productId, quantity));
    }

    @DeleteMapping("/{orderId}/items/{productId}")
    public ResponseEntity<Void> removeProductFromOrder(@PathVariable Long orderId,
                                                       @PathVariable Long productId) {
        orderProductService.removeProductFromOrder(orderId, productId);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{orderId}/items")
    public ResponseEntity<Void> clearOrder(@PathVariable Long orderId) {
        orderProductService.clearOrder(orderId);

        return ResponseEntity.noContent().build();
    }
}
