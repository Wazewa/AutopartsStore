package org.korolev.automagazine.api.services;

import lombok.AllArgsConstructor;
import org.korolev.automagazine.api.dto.OrderRequest;
import org.korolev.automagazine.api.dto.OrderResponse;
import org.korolev.automagazine.api.entities.CustomerEntity;
import org.korolev.automagazine.api.entities.OrderEntity;
import org.korolev.automagazine.api.entities.OrderStatus;
import org.korolev.automagazine.api.exceptions.OrderNotFoundException;
import org.korolev.automagazine.api.mappers.OrderMapper;
import org.korolev.automagazine.api.repositories.OrderRepository;
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
