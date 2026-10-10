package org.korolev.autopartsstore.api.event.service;

import lombok.AllArgsConstructor;
import org.korolev.autopartsstore.api.user.entity.UserEntity;
import org.korolev.autopartsstore.api.user.service.UserService;
import org.korolev.autopartsstore.api.event.dto.EventRequest;
import org.korolev.autopartsstore.api.event.dto.EventResponse;
import org.korolev.autopartsstore.api.event.entity.EventEntity;
import org.korolev.autopartsstore.api.event.exception.EventNotFoundException;
import org.korolev.autopartsstore.api.event.mapper.EventMapper;
import org.korolev.autopartsstore.api.event.repository.EventRepository;
import org.korolev.autopartsstore.api.security.CurrentUserService;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class EventService {

    private final EventRepository eventRepository;
    private final EventMapper eventMapper;
    private final UserService userService;

    @Transactional(readOnly = true)
    public List<EventResponse> findAllEvents() {
        return eventRepository.findAll().stream()
                .map(eventMapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public EventResponse findEventById(Long id) {
        return eventMapper.toResponse(
                eventRepository.findById(id)
                        .orElseThrow(
                        () -> new EventNotFoundException("Event not found.")
        ));
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
