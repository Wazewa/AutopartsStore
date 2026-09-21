package org.korolev.automagazine.api.mappers;

import org.korolev.automagazine.api.dto.EventRequest;
import org.korolev.automagazine.api.dto.EventResponse;
import org.korolev.automagazine.api.entities.EventEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface EventMapper {

    @Mapping(target = "customerId", source = "customer.id")
    EventResponse toResponse(EventEntity eventEntity);

    @Mapping(target = "customer", ignore = true)
    @Mapping(target = "eventDate", ignore = true)
    EventEntity toEntity(EventRequest eventRequest);
}
