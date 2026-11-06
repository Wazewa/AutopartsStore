package org.korolev.autopartsstore.event.service;

import lombok.AllArgsConstructor;
import org.korolev.autopartsstore.common.dto.PageResponse;
import org.korolev.autopartsstore.event.entity.EventType;
import org.korolev.autopartsstore.user.entity.UserEntity;
import org.korolev.autopartsstore.user.service.UserService;
import org.korolev.autopartsstore.event.dto.EventRequest;
import org.korolev.autopartsstore.event.dto.EventResponse;
import org.korolev.autopartsstore.event.entity.EventEntity;
import org.korolev.autopartsstore.event.exception.EventNotFoundException;
import org.korolev.autopartsstore.event.mapper.EventMapper;
import org.korolev.autopartsstore.event.repository.EventRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.ThreadLocalRandom;


@Service
@AllArgsConstructor
public class EventService {

    private final EventRepository eventRepository;
    private final EventMapper eventMapper;
    private final UserService userService;

    @Transactional(readOnly = true)
    @PreAuthorize("hasRole('ADMIN')")
    public PageResponse<EventResponse> findAllEvents(Pageable pageable) {
        return PageResponse.of(eventRepository.findAll(pageable), (eventMapper::toResponse));
    }

    @Transactional(readOnly = true)
    @PreAuthorize("hasRole('ADMIN')")
    public EventResponse findEventById(Long id) {
        return eventMapper.toResponse(
                eventRepository.findById(id)
                        .orElseThrow(
                        () -> new EventNotFoundException("Event not found.")
        ));
    }

    @Transactional
    public void trackEvent(EventType eventType, UserEntity user, Integer sessionId) {
        EventEntity eventEntity = EventEntity.builder()
                .eventType(eventType)
                .sessionId(sessionId != null ? sessionId :
                        ThreadLocalRandom.current().nextInt(1, 1000))
                .user(user)
                .build();
        eventRepository.save(eventEntity);
    }

    @Transactional
    public EventResponse createEvent(EventRequest eventRequest) {
        EventEntity eventEntity = eventMapper.toEntity(eventRequest);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated() && !(auth instanceof AnonymousAuthenticationToken)) {
            UserEntity user = userService.findByEmail(auth.getName());
            eventEntity.setUser(user);
        }

        return eventMapper.toResponse(eventRepository.save(eventEntity));
    }
}
