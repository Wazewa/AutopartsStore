package org.korolev.autopartsstore.api.auth.service;

import lombok.AllArgsConstructor;
import org.korolev.autopartsstore.api.auth.dto.AuthResponse;
import org.korolev.autopartsstore.api.auth.dto.LoginRequest;
import org.korolev.autopartsstore.api.auth.dto.RegisterRequest;
import org.korolev.autopartsstore.api.auth.mapper.AuthMapper;
import org.korolev.autopartsstore.api.customer.entity.CustomerEntity;
import org.korolev.autopartsstore.api.customer.exception.CustomerAlreadyExistsException;
import org.korolev.autopartsstore.api.customer.repository.CustomerRepository;
import org.korolev.autopartsstore.api.role.entity.CustomerRole;
import org.korolev.autopartsstore.api.role.entity.CustomerRoleId;
import org.korolev.autopartsstore.api.role.entity.RoleEntity;
import org.korolev.autopartsstore.api.role.repository.CustomerRoleRepository;
import org.korolev.autopartsstore.api.role.repository.RoleRepository;
import org.korolev.autopartsstore.api.security.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class AuthService {
    private final CustomerRepository customerRepository;
    private final CustomerRoleRepository customerRoleRepository;
    private final RoleRepository roleRepository;
    private final CustomUserDetailsService userDetails;
    private final AuthMapper authMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Transactional
    public AuthResponse register(RegisterRequest registerRequest) {

        if (customerRepository.existsByEmail(registerRequest.email())) {
            throw new CustomerAlreadyExistsException("Customer with " +
                    registerRequest.email() + " email already exists.");
        }

        CustomerEntity customerEntity = authMapper.toEntity(registerRequest);
        customerEntity.setPasswordHash(passwordEncoder.encode(registerRequest.password()));
        CustomerEntity saved = customerRepository.save(customerEntity);
        assignCustomerRole(saved);
        String token = jwtService.generateToken(userDetails.loadUserByUsername(saved.getEmail()));

        return new AuthResponse(token);
    }

    @Transactional(readOnly = true)
    public AuthResponse login(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.email(),
                        loginRequest.password()
                )
        );

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String token = jwtService.generateToken(userDetails);

        return new AuthResponse(token);
    }

    private void assignCustomerRole(CustomerEntity customer) {
        RoleEntity role = roleRepository.findByRoleCode("ROLE_CUSTOMER")
                .orElseThrow(() -> new IllegalStateException("ROLE_CUSTOMER not found"));

        CustomerRoleId id = new CustomerRoleId();
        id.setCustomerId(customer.getId());
        id.setRoleId(role.getId());

        CustomerRole link = new CustomerRole();
        link.setId(id);
        link.setCustomer(customer);
        link.setRole(role);

        customerRoleRepository.save(link);
    }
}
