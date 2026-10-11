package org.korolev.autopartsstore.api.cart.service;

import lombok.AllArgsConstructor;
import org.korolev.autopartsstore.api.user.service.UserService;
import org.korolev.autopartsstore.api.cart.dto.CartResponse;
import org.korolev.autopartsstore.api.cart.entity.CartEntity;
import org.korolev.autopartsstore.api.user.entity.UserEntity;
import org.korolev.autopartsstore.api.cart.exception.CartNotFoundException;
import org.korolev.autopartsstore.api.cart.mapper.CartMapper;
import org.korolev.autopartsstore.api.cart.repository.CartRepository;
import org.korolev.autopartsstore.api.security.CurrentUserService;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final CartMapper cartMapper;
    private final UserService userService;
    private final CurrentUserService currentUserService;

    @Transactional(readOnly = true)
    @PreAuthorize("hasRole('ADMIN')")
    public List<CartResponse> findAllCarts() {
        return cartRepository.findAll().stream()
                .map(cartMapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    @PreAuthorize("hasRole('ADMIN')")
    public CartResponse findCartById(Long id) {
        return cartMapper.toResponse(cartRepository.findById(id)
                .orElseThrow(
                () -> new CartNotFoundException("Cart not found.")
        ));
    }

    @Transactional(readOnly = true)
    @PreAuthorize("hasRole('CUSTOMER')")
    public CartResponse findMyCart() {
        UserEntity user = userService.findByEmail(currentUserService.getEmail());

        CartEntity cartEntity = cartRepository.findByUserId(user.getId())
                .orElseThrow(
                        () -> new CartNotFoundException("Cart not found.")
                );
        return cartMapper.toResponse(cartEntity);
    }

    @Transactional
    @PreAuthorize("hasRole('CUSTOMER')")
    public CartResponse getOrCreateCart() {
        UserEntity existUser = userService.findByEmail(currentUserService.getEmail());

        return cartRepository.findByUserId(existUser.getId())
                .map(cartMapper::toResponse)
                .orElseGet(() -> {
                        CartEntity cartEntity = CartEntity.builder()
                                .user(existUser)
                                .build();
                        return cartMapper.toResponse(cartRepository.save(cartEntity));
                });
    }

    @Transactional
    @PreAuthorize("hasRole('CUSTOMER')")
    public void deleteCart() {
        UserEntity existUser = userService.findByEmail(currentUserService.getEmail());

        CartEntity cart = cartRepository.findByUserId(existUser.getId()).orElseThrow(
                () -> new CartNotFoundException("Cart not found"));

        cartRepository.delete(cart);
    }

    @Transactional(readOnly = true)
    public CartEntity requireOwnCart(Long cartId) {
        CartEntity cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new CartNotFoundException("Cart not found."));

        if (!cart.getUser().getEmail().equals(currentUserService.getEmail())) {
            throw new AccessDeniedException("Not your cart");
        }

        return cart;
    }
}