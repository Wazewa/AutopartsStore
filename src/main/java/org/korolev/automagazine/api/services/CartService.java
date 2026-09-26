package org.korolev.automagazine.api.services;

import lombok.AllArgsConstructor;
import org.korolev.automagazine.api.dto.CartResponse;
import org.korolev.automagazine.api.entities.CartEntity;
import org.korolev.automagazine.api.entities.CustomerEntity;
import org.korolev.automagazine.api.exceptions.CartNotFoundException;
import org.korolev.automagazine.api.mappers.CartMapper;
import org.korolev.automagazine.api.repositories.CartRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final CartMapper cartMapper;
    private final CustomerService customerService;

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
    public CartResponse findCartByCustomerId(Long customerId) {
        CartEntity cartEntity = cartRepository.findByCustomerId(customerId)
                .orElseThrow(
                        () -> new CartNotFoundException("Cart not found.")
                );
        return cartMapper.toResponse(cartEntity);
    }

    @Transactional
    public CartResponse getOrCreateCart(Long customerId) {
        return cartRepository.findByCustomerId(customerId)
                .map(cartMapper::toResponse)
                .orElseGet(() -> {
                        CustomerEntity customer = customerService.findEntityById(customerId);

                        CartEntity cartEntity = CartEntity.builder()
                                .customer(customer)
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