package org.korolev.autopartsstore.cart.service;

import lombok.AllArgsConstructor;
import org.korolev.autopartsstore.cart.dto.CartProductRequest;
import org.korolev.autopartsstore.cart.dto.CartProductResponse;
import org.korolev.autopartsstore.cart.entity.CartEntity;
import org.korolev.autopartsstore.cart.entity.CartProductEntity;
import org.korolev.autopartsstore.event.entity.EventType;
import org.korolev.autopartsstore.event.service.EventService;
import org.korolev.autopartsstore.product.entity.ProductEntity;
import org.korolev.autopartsstore.cart.exception.CartProductNotFoundException;
import org.korolev.autopartsstore.cart.mapper.CartProductMapper;
import org.korolev.autopartsstore.cart.repository.CartProductRepository;
import org.korolev.autopartsstore.product.service.ProductService;
import org.korolev.autopartsstore.security.CurrentUserService;
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
    private final EventService eventService;
    private final CurrentUserService currentUserService;

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

        CartProductEntity savedItem;

        if(existing.isPresent()) {
            CartProductEntity item = existing.get();
            item.setQuantity(item.getQuantity() + cartProductRequest.quantity());
            savedItem = cartProductRepository.save(item);
        }
        else {
            CartProductEntity cartProductEntity = cartProductMapper.toEntity(cartProductRequest);
            cartProductEntity.setCart(cartEntity);
            cartProductEntity.setProduct(productEntity);
            cartProductEntity.setPriceAddAt(productEntity.getPrice());
            savedItem = cartProductRepository.save(cartProductEntity);
        }

        eventService.trackEvent(EventType.ADD_TO_CART,
                currentUserService.getCurrentUserOrNull(), null);

        return cartProductMapper.toResponse(savedItem);
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

        eventService.trackEvent(EventType.REMOVE_FROM_CART,
                currentUserService.getCurrentUserOrNull(), null);
    }

    @Transactional
    @PreAuthorize("hasRole('CUSTOMER')")
    public void clearCart(Long cartId) {
        cartService.requireOwnCart(cartId);

        List<CartProductEntity> items = cartProductRepository.findByCartId(cartId);
        cartProductRepository.deleteAll(items);
    }
}