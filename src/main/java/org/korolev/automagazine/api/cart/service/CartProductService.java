package org.korolev.automagazine.api.cart.service;

import lombok.AllArgsConstructor;
import org.korolev.automagazine.api.cart.dto.CartProductRequest;
import org.korolev.automagazine.api.cart.dto.CartProductResponse;
import org.korolev.automagazine.api.cart.entity.CartEntity;
import org.korolev.automagazine.api.cart.entity.CartProductEntity;
import org.korolev.automagazine.api.product.entity.ProductEntity;
import org.korolev.automagazine.api.cart.exception.CartProductNotFoundException;
import org.korolev.automagazine.api.cart.mapper.CartProductMapper;
import org.korolev.automagazine.api.cart.repository.CartProductRepository;
import org.korolev.automagazine.api.product.service.ProductService;
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
    public List<CartProductResponse> getCartItems(Long cartId) {
        return cartProductRepository.findByCartId(cartId)
                .stream()
                .map(cartProductMapper::toResponse)
                .toList();
    }

    @Transactional
    public CartProductResponse addProductToCart(Long cartId, CartProductRequest cartProductRequest) {

        CartEntity cartEntity = cartService.findEntityById(cartId);
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
    public CartProductResponse updateQuantity(Long cartId, Long productId, Integer quantity) {
        CartProductEntity existing = cartProductRepository.findByCartIdAndProductId(
                cartId, productId
        ).orElseThrow(
                () -> new CartProductNotFoundException("Cart product not found.")
        );

        existing.setQuantity(quantity);
        return cartProductMapper.toResponse(cartProductRepository.save(existing));
    }

    @Transactional
    public void removeProductFromCart(Long cartId, Long productId) {
        CartProductEntity existing = cartProductRepository.findByCartIdAndProductId(
                cartId, productId
        ).orElseThrow(
                () -> new CartProductNotFoundException("Cart product not found.")
        );
        cartProductRepository.delete(existing);
    }

    @Transactional
    public void clearCart(Long cartId) {
        cartService.findEntityById(cartId);

        List<CartProductEntity> items = cartProductRepository.findByCartId(cartId);
        cartProductRepository.deleteAll(items);
    }
}