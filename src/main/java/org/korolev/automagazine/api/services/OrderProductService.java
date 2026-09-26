package org.korolev.automagazine.api.services;

import lombok.AllArgsConstructor;
import org.korolev.automagazine.api.entities.OrderProductEntity;
import org.korolev.automagazine.api.exceptions.OrderProductNotFoundException;
import org.korolev.automagazine.api.exceptions.OrderProductAlreadyExistsException;
import org.korolev.automagazine.api.repositories.OrderProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class OrderProductService {

    private final OrderProductRepository orderProductRepository;

    @Transactional(readOnly = true)
    public List<OrderProductEntity> findAllOrderProducts() {
        return orderProductRepository.findAll();
    }

    @Transactional(readOnly = true)
    public OrderProductEntity findOrderProductById(OrderProductEntity orderProductEntity) {
        return orderProductRepository.findById(orderProductEntity.getId()).orElseThrow(
                () -> new OrderProductNotFoundException("Order product not found.")
        );
    }

    @Transactional
    public OrderProductEntity createOrderProduct(OrderProductEntity orderProductEntity) {
        if(orderProductRepository.existsById(orderProductEntity.getId()) && orderProductEntity.getId() != null) {
            throw new OrderProductAlreadyExistsException("Order product already exists.");
        }
        return orderProductRepository.save(orderProductEntity);
    }

    @Transactional
    public OrderProductEntity updateOrderProduct(OrderProductEntity orderProductEntity) {
        if(!orderProductRepository.existsById(orderProductEntity.getId())) {
            throw new OrderProductNotFoundException("Order product not found.");
        }
        return orderProductRepository.save(orderProductEntity);
    }

    @Transactional
    public void deleteOrderProduct(OrderProductEntity orderProductEntity) {
        if(!orderProductRepository.existsById(orderProductEntity.getId())) {
            throw new OrderProductNotFoundException("Order product not found.");
        }
        orderProductRepository.deleteById(orderProductEntity.getId());
    }
}