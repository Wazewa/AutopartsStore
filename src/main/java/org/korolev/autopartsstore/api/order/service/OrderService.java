package org.korolev.autopartsstore.api.order.service;

import lombok.AllArgsConstructor;
import org.korolev.autopartsstore.api.customer.service.CustomerService;
import org.korolev.autopartsstore.api.order.dto.OrderRequest;
import org.korolev.autopartsstore.api.order.dto.OrderResponse;
import org.korolev.autopartsstore.api.customer.entity.CustomerEntity;
import org.korolev.autopartsstore.api.order.entity.OrderEntity;
import org.korolev.autopartsstore.api.order.entity.OrderStatus;
import org.korolev.autopartsstore.api.order.exception.OrderNotFoundException;
import org.korolev.autopartsstore.api.order.mapper.OrderMapper;
import org.korolev.autopartsstore.api.order.repository.OrderRepository;
import org.korolev.autopartsstore.api.security.CurrentUserService;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Service
@AllArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final CustomerService customerService;
    private final CurrentUserService currentUserService;

    @Transactional(readOnly = true)
    @PreAuthorize("hasRole('ADMIN')")
    public List<OrderResponse> findAllOrders() {
        return orderRepository.findAll().stream()
                .map(orderMapper::toResponse)
                .toList();
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
    public List<OrderResponse> findCustomerOrders() {
        CustomerEntity customer = customerService.findByEmail(currentUserService.getEmail());

        return orderRepository.findByCustomerId(customer.getId()).stream()
                .map(orderMapper::toResponse)
                .toList();
    }

    @Transactional
    @PreAuthorize("hasRole('CUSTOMER')")
    public OrderResponse createOrder(OrderRequest orderRequest) {

        CustomerEntity customerEntity = customerService.findByEmail(currentUserService.getEmail());

        OrderEntity orderEntity = orderMapper.toEntity(orderRequest);
        orderEntity.setStatus(OrderStatus.PENDING);
        orderEntity.setCustomer(customerEntity);
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

        if (!order.getCustomer().getEmail().equals(currentUserService.getEmail())) {
            throw new AccessDeniedException("Not your order");
        }

        return order;
    }
}
