package org.korolev.automagazine.api.controllers;

import lombok.AllArgsConstructor;
import org.korolev.automagazine.api.dto.AdminRequest;
import org.korolev.automagazine.api.dto.AdminResponse;
import org.korolev.automagazine.api.services.AdminService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/admins")
public class AdminController {

    private final AdminService adminService;

    @GetMapping
    public ResponseEntity<List<AdminResponse>> getAdmins() {
        return ResponseEntity.ok(adminService.findAllAdmins());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AdminResponse> getAdminById(@PathVariable Long id) {
        return ResponseEntity.ok(adminService.findAdminById(id));
    }

    @PostMapping
    public ResponseEntity<AdminResponse> createAdmin(@RequestBody AdminRequest adminRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(adminService.createAdmin(adminRequest));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AdminResponse> updateAdmin(@PathVariable Long id, @RequestBody AdminRequest adminRequest) {
        return ResponseEntity.ok(adminService.updateAdmin(id, adminRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAdmin(@PathVariable Long id) {
        adminService.deleteAdmin(id);
        return ResponseEntity.noContent().build();
    }

}
