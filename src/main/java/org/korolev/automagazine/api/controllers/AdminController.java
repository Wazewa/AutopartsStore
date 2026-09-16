package org.korolev.automagazine.api.controllers;

import lombok.AllArgsConstructor;
import org.korolev.automagazine.api.dto.AdminRequest;
import org.korolev.automagazine.api.dto.AdminResponse;
import org.korolev.automagazine.api.services.AdminService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api")
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
    public AdminResponse createAdmin(@RequestBody AdminRequest adminRequest) {
        return adminService.createAdmin(adminRequest);
    }

    @PutMapping("/{id}")
    public AdminResponse updateAdmin(@PathVariable Long id, @RequestBody AdminRequest adminRequest) {
        return adminService.updateAdmin(id, adminRequest);
    }

    @DeleteMapping("/{id}")
    public void deleteAdmin(@PathVariable Long id) {
        adminService.deleteAdmin(id);
    }

}
