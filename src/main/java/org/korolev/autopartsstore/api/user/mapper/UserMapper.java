package org.korolev.autopartsstore.api.customer.mapper;

import org.korolev.autopartsstore.api.customer.dto.CustomerRequest;
import org.korolev.autopartsstore.api.customer.dto.CustomerResponse;
import org.korolev.autopartsstore.api.customer.entity.CustomerEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

    CustomerResponse toResponse(CustomerEntity customerEntity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "registeredAt", ignore = true)
    @Mapping(target = "orders", ignore = true)
    @Mapping(target = "events", ignore = true)
    @Mapping(target = "passwordHash", source = "password")
    CustomerEntity toEntity(CustomerRequest customerRequest);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "registeredAt", ignore = true)
    @Mapping(target = "orders", ignore = true)
    @Mapping(target = "events", ignore = true)
    @Mapping(target = "passwordHash", source = "password")
    void updateEntity(CustomerRequest customerRequest, @MappingTarget CustomerEntity customerEntity);
}
