package org.korolev.automagazine.api.controllers;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.korolev.automagazine.api.dto.CartProductRequest;
import org.korolev.automagazine.api.dto.CartProductResponse;
import org.korolev.automagazine.api.services.CartProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/carts")
@AllArgsConstructor
public class CartProductController {

    private final CartProductService cartProductService;

    @GetMapping("/{cartId}/items")
    public ResponseEntity<List<CartProductResponse>> getCartItems(@PathVariable Long cartId) {
        return ResponseEntity.ok(cartProductService.getCartItems(cartId));
    }

    @PostMapping ("/{cartId}/items")
    public ResponseEntity<CartProductResponse> addProductToCart(@PathVariable Long cartId,
                                                                 @Valid @RequestBody
                                                                 CartProductRequest cartProductRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(cartProductService.addProductToCart(
                cartId, cartProductRequest)
        );
    }

    @PutMapping("/{cartId}/items/{productId}")
    public ResponseEntity<CartProductResponse> updateQuantity(@PathVariable Long cartId,
                                                              @PathVariable Long productId,
                                                              @RequestParam Integer quantity) {
        return ResponseEntity.ok(cartProductService.updateQuantity(cartId, productId, quantity));
    }

    @DeleteMapping("/{cartId}/items/{productId}")
    public ResponseEntity<Void> removeProductFromCart(@PathVariable Long cartId,
                                                              @PathVariable Long productId) {
        cartProductService.removeProductFromCart(cartId, productId);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{cartId}/items")
    public ResponseEntity<Void> clearCart(@PathVariable Long cartId) {
        cartProductService.clearCart(cartId);

        return ResponseEntity.noContent().build();
    }
}
