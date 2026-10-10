package org.korolev.autopartsstore.api.order.mapper;

import org.korolev.autopartsstore.api.order.dto.OrderRequest;
import org.korolev.autopartsstore.api.order.dto.OrderResponse;
import org.korolev.autopartsstore.api.order.entity.OrderEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    @Mapping(target = "adminId", source = "admin.id")
    @Mapping(target = "customerId", source = "customer.id")
    OrderResponse toResponse(OrderEntity orderEntity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "orderProductEntities", ignore = true)
    @Mapping(target = "admin", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "customer", ignore = true)
    @Mapping(target = "orderDate", ignore = true)
    OrderEntity toEntity(OrderRequest orderRequest);
}
