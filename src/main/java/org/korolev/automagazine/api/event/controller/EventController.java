package org.korolev.automagazine.api.event.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.korolev.automagazine.api.event.dto.EventRequest;
import org.korolev.automagazine.api.event.dto.EventResponse;
import org.korolev.automagazine.api.event.service.EventService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@RestController
@Tag(name = "Events", description = "Сбор событий пользователей (только чтение и создание)")
@AllArgsConstructor
@RequestMapping("/api/events")
public class EventController {

    private final EventService eventService;

    @ApiResponses(
            @ApiResponse(responseCode = "200", description = "Все пользовательские события успешно отображены")
    )
    @Operation(summary = "Получить все события", description = "Возвращает все пользовательские события")
    @GetMapping
    public ResponseEntity<List<EventResponse>> getAllEvents() {
        return ResponseEntity.ok(eventService.findAllEvents());
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
            @ApiResponse(responseCode = "400", description = "Невалидные данные"),
            @ApiResponse(responseCode = "404", description = "Пользователь с заданным ID не найден")
    })
    @Operation(summary = "Создать событие",
            description = "Создает событие. События иммутабельны и не могут быть изменены или удалены")
    @PostMapping
    public ResponseEntity<EventResponse> createEvent(@Valid @RequestBody EventRequest eventRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(eventService.createEvent(eventRequest));
    }
}
