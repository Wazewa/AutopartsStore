package org.korolev.automagazine.api.controllers;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.korolev.automagazine.api.dto.CompatibilityRequest;
import org.korolev.automagazine.api.dto.CompatibilityResponse;
import org.korolev.automagazine.api.services.CompatibilityService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/compatibilities")
public class CompatibilityController {

    private final CompatibilityService compatibilityService;

    @GetMapping
    public ResponseEntity<List<CompatibilityResponse>> getAllCompatibilities() {
        return ResponseEntity.ok(compatibilityService.findAllCompatibilities());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CompatibilityResponse> getCompatibilityById(@PathVariable Long id) {
        return ResponseEntity.ok(compatibilityService.findCompatibilityById(id));
    }

    @PostMapping
    public ResponseEntity<CompatibilityResponse> createCompatibility(@Valid @RequestBody CompatibilityRequest compatibilityRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(compatibilityService.createCompatibility(compatibilityRequest));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CompatibilityResponse> updateCompatibility(@PathVariable Long id, @Valid @RequestBody CompatibilityRequest compatibilityRequest) {
        return ResponseEntity.ok(compatibilityService.updateCompatibility(id, compatibilityRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCompatibility(@PathVariable Long id) {
        compatibilityService.deleteCompatibility(id);
        return ResponseEntity.noContent().build();
    }
}
