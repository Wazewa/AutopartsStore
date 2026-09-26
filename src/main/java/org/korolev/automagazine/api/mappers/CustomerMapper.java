package org.korolev.automagazine.api.mappers;

import org.korolev.automagazine.api.dto.CustomerRequest;
import org.korolev.automagazine.api.dto.CustomerResponse;
import org.korolev.automagazine.api.entities.CustomerEntity;
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
