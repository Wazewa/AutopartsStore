package org.korolev.autopartsstore.api.order.service;

import lombok.AllArgsConstructor;
import org.korolev.autopartsstore.api.order.dto.OrderProductRequest;
import org.korolev.autopartsstore.api.order.dto.OrderProductResponse;
import org.korolev.autopartsstore.api.order.entity.OrderEntity;
import org.korolev.autopartsstore.api.order.entity.OrderProductEntity;
import org.korolev.autopartsstore.api.order.entity.OrderStatus;
import org.korolev.autopartsstore.api.product.entity.ProductEntity;
import org.korolev.autopartsstore.api.order.exception.OrderNotModifiableException;
import org.korolev.autopartsstore.api.order.exception.OrderProductNotFoundException;
import org.korolev.autopartsstore.api.order.mapper.OrderProductMapper;
import org.korolev.autopartsstore.api.product.service.ProductService;
import org.korolev.autopartsstore.api.order.repository.OrderProductRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class OrderProductService {

    private final OrderProductRepository orderProductRepository;
    private final OrderService orderService;
    private final ProductService productService;
    private final OrderProductMapper orderProductMapper;

    @Transactional(readOnly = true)
    @PreAuthorize("hasRole('CUSTOMER')")
    public List<OrderProductResponse> findOrderItems(Long orderId) {
        orderService.requireOwnOrder(orderId);
        return orderProductRepository.findByOrderId(orderId).stream()
                .map(orderProductMapper::toResponse)
                .toList();
    }

    @Transactional
    @PreAuthorize("hasRole('CUSTOMER')")
    public OrderProductResponse addProductToOrder(Long orderId, OrderProductRequest orderProductRequest) {
        OrderEntity orderEntity = orderService.requireOwnOrder(orderId);

        validateOrderIsModifiable(orderEntity);

        ProductEntity productEntity = productService.findEntityById(orderProductRequest.productId());

        Optional<OrderProductEntity> existing = orderProductRepository.findByOrderIdAndProductId(
                orderId, orderProductRequest.productId()
        );

        if(existing.isPresent()) {
            OrderProductEntity item = existing.get();
            item.setQuantity(item.getQuantity() + orderProductRequest.quantity());
            return orderProductMapper.toResponse(orderProductRepository.save(item));
        }
        else {
            OrderProductEntity orderProductEntity = orderProductMapper.toEntity(orderProductRequest);
            orderProductEntity.setOrder(orderEntity);
            orderProductEntity.setProduct(productEntity);
            orderProductEntity.setPriceAddAt(productEntity.getPrice());

            return orderProductMapper.toResponse(orderProductRepository.save(orderProductEntity));
        }
    }

    @Transactional
    @PreAuthorize("hasRole('CUSTOMER')")
    public OrderProductResponse updateQuantity(Long orderId, Long productId, Integer quantity) {

        OrderEntity orderEntity = orderService.requireOwnOrder(orderId);
        validateOrderIsModifiable(orderEntity);

        OrderProductEntity existing = orderProductRepository.findByOrderIdAndProductId(
                orderId, productId
        ).orElseThrow(
                () -> new OrderProductNotFoundException("Order product not found.")
        );

        existing.setQuantity(quantity);
        return orderProductMapper.toResponse(orderProductRepository.save(existing));
    }

    @Transactional
    @PreAuthorize("hasRole('CUSTOMER')")
    public void removeProductFromOrder(Long orderId, Long productId) {

        OrderEntity orderEntity = orderService.requireOwnOrder(orderId);
        validateOrderIsModifiable(orderEntity);

        OrderProductEntity existing = orderProductRepository.findByOrderIdAndProductId(
                orderId, productId
        ).orElseThrow(
                () -> new OrderProductNotFoundException("Order product not found.")
        );
        orderProductRepository.delete(existing);
    }

    @Transactional
    @PreAuthorize("hasRole('CUSTOMER')")
    public void clearOrder(Long orderId) {

        OrderEntity orderEntity = orderService.requireOwnOrder(orderId);
        validateOrderIsModifiable(orderEntity);

        List<OrderProductEntity> items = orderProductRepository.findByOrderId(orderId);
        orderProductRepository.deleteAll(items);
    }

    private void validateOrderIsModifiable(OrderEntity orderEntity) {
        if(orderEntity.getStatus() != OrderStatus.PENDING
                && orderEntity.getStatus() != OrderStatus.PROCESSING) {
            throw new OrderNotModifiableException("Cannot modify order with status: " + orderEntity.getStatus());
        }
    }
}