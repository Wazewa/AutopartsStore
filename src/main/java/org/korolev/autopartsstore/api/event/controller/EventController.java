package org.korolev.autopartsstore.api.event.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.korolev.autopartsstore.api.dto.PageResponse;
import org.korolev.autopartsstore.api.event.dto.EventRequest;
import org.korolev.autopartsstore.api.event.dto.EventResponse;
import org.korolev.autopartsstore.api.event.service.EventService;
import org.korolev.autopartsstore.api.factory.PageableFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @ApiResponses(
            @ApiResponse(responseCode = "200", description = "Все пользовательские события " +
                    "успешно отображены (с пагинацией)")
    )
    @Operation(summary = "Получить все события", description = "Возвращает все пользовательские " +
            "события (с пагинацией)")
    @GetMapping
    public ResponseEntity<PageResponse<EventResponse>> getAllEvents(
            @Parameter(description = "Номер страницы", example = "0")
            @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Размер страницы", example = "25")
            @RequestParam(defaultValue = "25") int size,
            @Parameter(description = "Поле сортировки", example = "eventDate")
            @RequestParam(defaultValue = "id") String sortBy,
            @Parameter(description = "Направление сортировки", example = "desc")
            @RequestParam(defaultValue = "asc") String direction)
    {
        Pageable pageable = PageableFactory.create(page, size, sortBy, direction, ALLOWED_SORT_FIELDS);
        return ResponseEntity.ok(eventService.findAllEvents(pageable));
    }

    @ApiResponses({
            @ApiResponse(responseCode = "200",
                    description = "Пользовательское событие с заданным ID успешно отображено"),
            @ApiResponse(responseCode = "404", description = "Событие с заданным ID не найдено")
    })
    @Operation(summary = "Получить событие по ID",
            description = "Возвращает пользовательское событие по заданному ID")
    @GetMapping("/{id}")
    public ResponseEntity<EventResponse> getEventById(
            @Parameter(description = "ID события", example = "1") @PathVariable Long id) {
        return ResponseEntity.ok(eventService.findEventById(id));
    }

    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Пользовательское событие успешно создано"),
            @ApiResponse(responseCode = "400", description = "Невалидные данные")
    })
    @Operation(summary = "Создать событие",
            description = "Создает событие. События иммутабельны и не могут быть изменены или удалены")
    @PostMapping
    public ResponseEntity<EventResponse> createEvent(@Valid @RequestBody EventRequest eventRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(eventService.createEvent(eventRequest));
    }
}
