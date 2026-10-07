package org.korolev.automagazine.api.event.mapper;

import org.korolev.automagazine.api.event.dto.EventRequest;
import org.korolev.automagazine.api.event.dto.EventResponse;
import org.korolev.automagazine.api.event.entity.EventEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EventMapper {

    @Mapping(target = "customerId", source = "customer.id")
    EventResponse toResponse(EventEntity eventEntity);

    @Mapping(target = "customer", ignore = true)
    @Mapping(target = "eventDate", ignore = true)
    EventEntity toEntity(EventRequest eventRequest);
}
