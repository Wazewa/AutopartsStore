package org.korolev.autopartsstore.api.auth.service;

import lombok.AllArgsConstructor;
import org.korolev.autopartsstore.api.auth.dto.AuthResponse;
import org.korolev.autopartsstore.api.auth.dto.LoginRequest;
import org.korolev.autopartsstore.api.auth.dto.RegisterRequest;
import org.korolev.autopartsstore.api.auth.mapper.AuthMapper;
import org.korolev.autopartsstore.api.user.entity.UserEntity;
import org.korolev.autopartsstore.api.user.exception.UserAlreadyExistsException;
import org.korolev.autopartsstore.api.user.repository.UserRepository;
import org.korolev.autopartsstore.api.role.entity.RoleEntity;
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
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final CustomUserDetailsService userDetails;
    private final AuthMapper authMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Transactional
    public AuthResponse register(RegisterRequest registerRequest) {

        if (userRepository.existsByEmail(registerRequest.email())) {
            throw new UserAlreadyExistsException("User with " +
                    registerRequest.email() + " email already exists.");
        }

        UserEntity userEntity = authMapper.toEntity(registerRequest);
        userEntity.setPasswordHash(passwordEncoder.encode(registerRequest.password()));

        RoleEntity userRole = roleRepository.findByRoleCode("ROLE_CUSTOMER")
                .orElseThrow(() -> new IllegalStateException("ROLE_CUSTOMER not found"));
        userEntity.setRole(userRole);

        UserEntity saved = userRepository.save(userEntity);

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
}
