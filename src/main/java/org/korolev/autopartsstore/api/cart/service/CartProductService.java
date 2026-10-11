package org.korolev.autopartsstore.api.cart.service;

import lombok.AllArgsConstructor;
import org.korolev.autopartsstore.api.cart.dto.CartProductRequest;
import org.korolev.autopartsstore.api.cart.dto.CartProductResponse;
import org.korolev.autopartsstore.api.cart.entity.CartEntity;
import org.korolev.autopartsstore.api.cart.entity.CartProductEntity;
import org.korolev.autopartsstore.api.product.entity.ProductEntity;
import org.korolev.autopartsstore.api.cart.exception.CartProductNotFoundException;
import org.korolev.autopartsstore.api.cart.mapper.CartProductMapper;
import org.korolev.autopartsstore.api.cart.repository.CartProductRepository;
import org.korolev.autopartsstore.api.product.service.ProductService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CartProductService {

    private final CartProductRepository cartProductRepository;
    private final CartProductMapper cartProductMapper;
    private final CartService cartService;
    private final ProductService productService;

    @Transactional(readOnly = true)
    @PreAuthorize("hasRole('CUSTOMER')")
    public List<CartProductResponse> getCartItems(Long cartId) {
        cartService.requireOwnCart(cartId);

        return cartProductRepository.findByCartId(cartId)
                .stream()
                .map(cartProductMapper::toResponse)
                .toList();
    }

    @Transactional
    @PreAuthorize("hasRole('CUSTOMER')")
    public CartProductResponse addProductToCart(Long cartId, CartProductRequest cartProductRequest) {
        CartEntity cartEntity = cartService.requireOwnCart(cartId);

        ProductEntity productEntity = productService.findEntityById(cartProductRequest.productId());

        Optional<CartProductEntity> existing = cartProductRepository.findByCartIdAndProductId(
                cartId, cartProductRequest.productId()
        );

        if(existing.isPresent()) {
            CartProductEntity item = existing.get();
            item.setQuantity(item.getQuantity() + cartProductRequest.quantity());
            return cartProductMapper.toResponse(cartProductRepository.save(item));
        }
        else {
            CartProductEntity cartProductEntity = cartProductMapper.toEntity(cartProductRequest);
            cartProductEntity.setCart(cartEntity);
            cartProductEntity.setProduct(productEntity);
            cartProductEntity.setPriceAddAt(productEntity.getPrice());

            return cartProductMapper.toResponse(cartProductRepository.save(cartProductEntity));
        }
    }

    @Transactional
    @PreAuthorize("hasRole('CUSTOMER')")
    public CartProductResponse updateQuantity(Long cartId, Long productId, Integer quantity) {
        cartService.requireOwnCart(cartId);

        CartProductEntity existing = cartProductRepository.findByCartIdAndProductId(
                cartId, productId
        ).orElseThrow(
                () -> new CartProductNotFoundException("Cart product not found.")
        );

        existing.setQuantity(quantity);
        return cartProductMapper.toResponse(cartProductRepository.save(existing));
    }

    @Transactional
    @PreAuthorize("hasRole('CUSTOMER')")
    public void removeProductFromCart(Long cartId, Long productId) {
        cartService.requireOwnCart(cartId);

        CartProductEntity existing = cartProductRepository.findByCartIdAndProductId(
                cartId, productId
        ).orElseThrow(
                () -> new CartProductNotFoundException("Cart product not found.")
        );
        cartProductRepository.delete(existing);
    }

    @Transactional
    @PreAuthorize("hasRole('CUSTOMER')")
    public void clearCart(Long cartId) {
        cartService.requireOwnCart(cartId);

        List<CartProductEntity> items = cartProductRepository.findByCartId(cartId);
        cartProductRepository.deleteAll(items);
    }
}