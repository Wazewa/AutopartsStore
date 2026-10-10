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
    public List<CartResponse> findAllCarts() {
        return cartRepository.findAll().stream()
                .map(cartMapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public CartResponse findCartById(Long id) {
        return cartMapper.toResponse(cartRepository.findById(id)
                .orElseThrow(
                () -> new CartNotFoundException("Cart not found.")
        ));
    }

    @Transactional(readOnly = true)
    public CartEntity findEntityById(Long id) {
        return cartRepository.findById(id).orElseThrow(
                () -> new CartNotFoundException("Cart not found.")
        );
    }

    @Transactional(readOnly = true)
    public CartResponse findCartByUserId(Long userId) {
        CartEntity cartEntity = cartRepository.findByUserId(userId)
                .orElseThrow(
                        () -> new CartNotFoundException("Cart not found.")
                );
        return cartMapper.toResponse(cartEntity);
    }

    @Transactional
    public CartResponse getOrCreateCart(Long userId) {
        return cartRepository.findByUserId(userId)
                .map(cartMapper::toResponse)
                .orElseGet(() -> {
                        UserEntity user = userService.findByEmail(currentUserService.getEmail());

                        CartEntity cartEntity = CartEntity.builder()
                                .user(user)
                                .build();
                        return cartMapper.toResponse(cartRepository.save(cartEntity));
                });
    }

    @Transactional
    public void deleteCart(Long id) {
        if(!cartRepository.existsById(id)) {
            throw new CartNotFoundException("Cart not found.");
        }
        cartRepository.deleteById(id);
    }
}