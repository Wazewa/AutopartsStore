package org.korolev.autopartsstore.api.event.mapper;

import org.korolev.autopartsstore.api.event.dto.EventRequest;
import org.korolev.autopartsstore.api.event.dto.EventResponse;
import org.korolev.autopartsstore.api.event.entity.EventEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EventMapper {

    @Mapping(target = "userId", source = "user.id")
    EventResponse toResponse(EventEntity eventEntity);

    @Mapping(target = "user", ignore = true)
    @Mapping(target = "eventDate", ignore = true)
    EventEntity toEntity(EventRequest eventRequest);
}
