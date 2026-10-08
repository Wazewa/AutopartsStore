package org.korolev.autopartsstore.api.event.service;

import lombok.AllArgsConstructor;
import org.korolev.autopartsstore.api.customer.service.CustomerService;
import org.korolev.autopartsstore.api.event.dto.EventRequest;
import org.korolev.autopartsstore.api.event.dto.EventResponse;
import org.korolev.autopartsstore.api.customer.entity.CustomerEntity;
import org.korolev.autopartsstore.api.event.entity.EventEntity;
import org.korolev.autopartsstore.api.event.exception.EventNotFoundException;
import org.korolev.autopartsstore.api.event.mapper.EventMapper;
import org.korolev.autopartsstore.api.event.repository.EventRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class EventService {

    private final EventRepository eventRepository;
    private final EventMapper eventMapper;
    private final CustomerService customerService;

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

        CustomerEntity customerEntity = null;

        if(eventRequest.customerId() != null) {
            customerEntity = customerService.findEntityById(eventRequest.customerId());
        }

        EventEntity eventEntity = eventMapper.toEntity(eventRequest);
        eventEntity.setCustomer(customerEntity);

        return eventMapper.toResponse(eventRepository.save(eventEntity));
    }
}
