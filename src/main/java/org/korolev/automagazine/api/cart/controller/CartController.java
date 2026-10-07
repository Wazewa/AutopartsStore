package org.korolev.automagazine.api.cart.controller;

import lombok.AllArgsConstructor;
import org.korolev.automagazine.api.cart.dto.CartResponse;
import org.korolev.automagazine.api.cart.service.CartService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/carts")
public class CartController {

    private final CartService cartService;

    @GetMapping
    public ResponseEntity<List<CartResponse>> getAllCarts() {
        return ResponseEntity.ok(cartService.findAllCarts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CartResponse> getCartById(@PathVariable Long id) {
        return ResponseEntity.ok(cartService.findCartById(id));
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<CartResponse> getCartByCustomerId(@PathVariable Long customerId) {
        return ResponseEntity.ok(cartService.findCartByCustomerId(customerId));
    }

    @PostMapping("/customer/{customerId}")
    public ResponseEntity<CartResponse> getOrCreateCart(@PathVariable Long customerId) {
        return ResponseEntity.status(HttpStatus.CREATED).body(cartService.getOrCreateCart(customerId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCart(@PathVariable Long id) {
        cartService.deleteCart(id);

        return ResponseEntity.noContent().build();
    }
}
