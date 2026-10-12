package org.korolev.autopartsstore.api.order.service;

import lombok.AllArgsConstructor;
import org.korolev.autopartsstore.api.dto.PageResponse;
import org.korolev.autopartsstore.api.user.entity.UserEntity;
import org.korolev.autopartsstore.api.user.service.UserService;
import org.korolev.autopartsstore.api.order.dto.OrderRequest;
import org.korolev.autopartsstore.api.order.dto.OrderResponse;
import org.korolev.autopartsstore.api.order.entity.OrderEntity;
import org.korolev.autopartsstore.api.order.entity.OrderStatus;
import org.korolev.autopartsstore.api.order.exception.OrderNotFoundException;
import org.korolev.autopartsstore.api.order.mapper.OrderMapper;
import org.korolev.autopartsstore.api.order.repository.OrderRepository;
import org.korolev.autopartsstore.api.security.CurrentUserService;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
@AllArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final UserService userService;
    private final CurrentUserService currentUserService;

    @Transactional(readOnly = true)
    @PreAuthorize("hasRole('ADMIN')")
    public PageResponse<OrderResponse> findAllOrders(Pageable pageable) {
        return PageResponse.of(orderRepository.findAll(pageable), orderMapper::toResponse);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Transactional(readOnly = true)
    public OrderResponse findOrderById(Long id) {
        return orderMapper.toResponse(orderRepository.findById(id).orElseThrow(
                () -> new OrderNotFoundException("Order not found.")
        ));
    }

    @Transactional(readOnly = true)
    @PreAuthorize("hasRole('CUSTOMER')")
    public PageResponse<OrderResponse> findMyOrders(Pageable pageable) {
        UserEntity user = userService.findByEmail(currentUserService.getEmail());

        return PageResponse.of(orderRepository.findByUserId(user.getId(), pageable), orderMapper::toResponse);
    }

    @Transactional
    @PreAuthorize("hasRole('CUSTOMER')")
    public OrderResponse createOrder(OrderRequest orderRequest) {

        UserEntity userEntity = userService.findByEmail(currentUserService.getEmail());

        OrderEntity orderEntity = orderMapper.toEntity(orderRequest);
        orderEntity.setStatus(OrderStatus.PENDING);
        orderEntity.setUser(userEntity);
        orderEntity.setOrderDate(Instant.now());

        return orderMapper.toResponse(orderRepository.save(orderEntity));
    }

    @PreAuthorize("hasRole('CUSTOMER')")
    @Transactional
    public void deleteOrderById(Long id) {
        OrderEntity order = requireOwnOrder(id);
        orderRepository.delete(order);
    }

    @Transactional(readOnly = true)
    public OrderEntity requireOwnOrder(Long id) {
        OrderEntity order = orderRepository.findById(id).orElseThrow(
                () -> new OrderNotFoundException("Order not found")
        );

        if (!order.getUser().getEmail().equals(currentUserService.getEmail())) {
            throw new AccessDeniedException("Not your order");
        }

        return order;
    }
}
