package org.korolev.autopartsstore.api.admin.service;

import lombok.AllArgsConstructor;
import org.korolev.autopartsstore.api.admin.dto.AdminRequest;
import org.korolev.autopartsstore.api.admin.dto.AdminResponse;
import org.korolev.autopartsstore.api.admin.entity.AdminEntity;
import org.korolev.autopartsstore.api.admin.exception.AdminAlreadyExistsException;
import org.korolev.autopartsstore.api.admin.exception.AdminNotFoundException;
import org.korolev.autopartsstore.api.admin.mapper.AdminMapper;
import org.korolev.autopartsstore.api.admin.repository.AdminRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class AdminService {

    private final AdminRepository adminRepository;
    private final AdminMapper adminMapper;

    @Transactional(readOnly = true)
    public List<AdminResponse> findAllAdmins() {
        return adminRepository.findAll().stream()
                .map(adminMapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public AdminResponse findAdminById(Long id) {
        return adminMapper.toResponse(
                adminRepository.findById(id)
                        .orElseThrow(
                        () -> new AdminNotFoundException("Admin not found.")
                ));
    }

    @Transactional
    public AdminResponse createAdmin(AdminRequest adminRequest) {

        AdminEntity adminEntity = adminMapper.toEntity(adminRequest);

        if(adminRepository.existsByEmail(adminEntity.getEmail())) {
            throw new AdminAlreadyExistsException("Admin with " + adminEntity.getEmail() +" email already exists.");
        }
        return adminMapper.toResponse(adminRepository.save(adminEntity));
    }

    @Transactional
    public AdminResponse updateAdmin(Long id, AdminRequest adminRequest) {

        AdminEntity adminEntity = adminRepository.findById(id).orElseThrow(
                () -> new AdminNotFoundException("Admin not found.")
        );

        adminMapper.updateEntity(adminRequest, adminEntity);

        return adminMapper.toResponse(adminRepository.save(adminEntity));
    }

    @Transactional
    public void deleteAdmin(Long id) {
        if(!adminRepository.existsById(id)) {
            throw new AdminNotFoundException("Admin not found.");
        }
         adminRepository.deleteById(id);
    }

}
