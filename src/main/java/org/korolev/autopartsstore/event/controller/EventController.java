package org.korolev.autopartsstore.event.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.korolev.autopartsstore.common.dto.PageResponse;
import org.korolev.autopartsstore.event.dto.EventRequest;
import org.korolev.autopartsstore.event.dto.EventResponse;
import org.korolev.autopartsstore.event.service.EventService;
import org.korolev.autopartsstore.common.factory.PageableFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Pageable;

import java.util.Set;

@RestController
@Tag(name = "Events", description = "Сбор событий пользователей (только чтение и создание)")
@AllArgsConstructor
@RequestMapping("/api/events")
public class EventController {
    private static final Set<String> ALLOWED_SORT_FIELDS =
        Set.of("id", "eventDate", "eventType", "sessionId");

    private final EventService eventService;

    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Все пользовательские события " +
                    "успешно отображены (с пагинацией)"),
            @ApiResponse(responseCode = "403", description = "Доступ запрещен")
    })
    @Operation(summary = "Получить все события", description = "Возвращает все пользовательские " +
            "события (с пагинацией)")
    @GetMapping
    public PageResponse<EventResponse> getAllEvents(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "25") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction)
    {
        Pageable pageable = PageableFactory.create(page, size, sortBy, direction, ALLOWED_SORT_FIELDS);
        return eventService.findAllEvents(pageable);
    }

    @ApiResponses({
            @ApiResponse(responseCode = "200",
                    description = "Пользовательское событие с заданным ID успешно отображено"),
            @ApiResponse(responseCode = "403", description = "Доступ запрещен"),
            @ApiResponse(responseCode = "404", description = "Событие с заданным ID не найдено")
    })
    @Operation(summary = "Получить событие по ID",
            description = "Возвращает пользовательское событие по заданному ID")
    @GetMapping("/{id}")
    public EventResponse getEventById(
            @PathVariable Long id) {
        return eventService.findEventById(id);
    }

    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Пользовательское событие успешно создано"),
            @ApiResponse(responseCode = "400", description = "Невалидные данные")
    })
    @Operation(summary = "Создать событие",
            description = "Создает событие. События иммутабельны и не могут быть изменены или удалены")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EventResponse createEvent(@Valid @RequestBody EventRequest eventRequest) {
        return eventService.createEvent(eventRequest);
    }
}
