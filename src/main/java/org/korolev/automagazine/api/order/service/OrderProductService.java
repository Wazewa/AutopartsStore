package org.korolev.automagazine.api.order.service;

import lombok.AllArgsConstructor;
import org.korolev.automagazine.api.order.dto.OrderProductRequest;
import org.korolev.automagazine.api.order.dto.OrderProductResponse;
import org.korolev.automagazine.api.order.entity.OrderEntity;
import org.korolev.automagazine.api.order.entity.OrderProductEntity;
import org.korolev.automagazine.api.order.entity.OrderStatus;
import org.korolev.automagazine.api.product.entity.ProductEntity;
import org.korolev.automagazine.api.order.exception.OrderNotModifiableException;
import org.korolev.automagazine.api.order.exception.OrderProductNotFoundException;
import org.korolev.automagazine.api.order.mapper.OrderProductMapper;
import org.korolev.automagazine.api.product.service.ProductService;
import org.korolev.automagazine.api.order.repository.OrderProductRepository;
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
    public List<OrderProductResponse> findOrderItems(Long orderId) {
        return orderProductRepository.findByOrderId(orderId).stream()
                .map(orderProductMapper::toResponse)
                .toList();
    }

    @Transactional
    public OrderProductResponse addProductToOrder(Long orderId, OrderProductRequest orderProductRequest) {
        OrderEntity orderEntity = orderService.findEntityById(orderId);

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
    public OrderProductResponse updateQuantity(Long orderId, Long productId, Integer quantity) {

        OrderEntity orderEntity = orderService.findEntityById(orderId);
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
    public void removeProductFromOrder(Long orderId, Long productId) {

        OrderEntity orderEntity = orderService.findEntityById(orderId);
        validateOrderIsModifiable(orderEntity);

        OrderProductEntity existing = orderProductRepository.findByOrderIdAndProductId(
                orderId, productId
        ).orElseThrow(
                () -> new OrderProductNotFoundException("Order product not found.")
        );
        orderProductRepository.delete(existing);
    }

    @Transactional
    public void clearOrder(Long orderId) {

        OrderEntity orderEntity = orderService.findEntityById(orderId);
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