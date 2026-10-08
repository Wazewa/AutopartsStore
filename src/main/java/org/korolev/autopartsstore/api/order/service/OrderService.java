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

    @Transactional(readOnly = true)
    public List<OrderResponse> findAllOrders() {
        return orderRepository.findAll().stream()
                .map(orderMapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public OrderResponse findOrderById(Long id) {
        return orderMapper.toResponse(orderRepository.findById(id).orElseThrow(
                () -> new OrderNotFoundException("Order not found.")
        ));
    }

    @Transactional(readOnly = true)
    public OrderEntity findEntityById(Long id) {
        return orderRepository.findById(id).orElseThrow(
                () -> new OrderNotFoundException("Order not found.")
        );
    }

    @Transactional(readOnly = true)
    public List<OrderResponse> findOrdersByCustomerId(Long customerId) {
        return orderRepository.findByCustomerId(customerId).stream()
                .map(orderMapper::toResponse)
                .toList();
    }

    @Transactional
    public OrderResponse createOrder(OrderRequest orderRequest) {

        CustomerEntity customerEntity = customerService.findEntityById(orderRequest.customerId());

        OrderEntity orderEntity = orderMapper.toEntity(orderRequest);
        orderEntity.setStatus(OrderStatus.PENDING);
        orderEntity.setCustomer(customerEntity);
        orderEntity.setOrderDate(Instant.now());

        return orderMapper.toResponse(orderRepository.save(orderEntity));
    }

    @Transactional
    public void deleteOrderById(Long id) {
        if(!orderRepository.existsById(id)) {
            throw new OrderNotFoundException("Order not found.");
        }
        orderRepository.deleteById(id);
    }
}
