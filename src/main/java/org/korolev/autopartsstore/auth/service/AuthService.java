package org.korolev.autopartsstore.auth.service;

import lombok.AllArgsConstructor;
import org.korolev.autopartsstore.auth.dto.AuthResponse;
import org.korolev.autopartsstore.auth.dto.LoginRequest;
import org.korolev.autopartsstore.auth.dto.RegisterRequest;
import org.korolev.autopartsstore.auth.mapper.AuthMapper;
import org.korolev.autopartsstore.event.entity.EventType;
import org.korolev.autopartsstore.event.service.EventService;

import org.korolev.autopartsstore.user.entity.UserEntity;
import org.korolev.autopartsstore.user.exception.UserAlreadyExistsException;
import org.korolev.autopartsstore.user.repository.UserRepository;
import org.korolev.autopartsstore.role.entity.RoleEntity;
import org.korolev.autopartsstore.role.repository.RoleRepository;
import org.korolev.autopartsstore.security.JwtService;
import org.korolev.autopartsstore.user.service.UserService;
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
    private final UserService userService;
    private final EventService eventService;

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

        eventService.trackEvent(EventType.REGISTRATION,
                saved, null);

        return new AuthResponse(token);
    }

    @Transactional
    public AuthResponse login(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.email(),
                        loginRequest.password()
                )
        );

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String token = jwtService.generateToken(userDetails);

        UserEntity user = userService.findByEmail(userDetails.getUsername());

        eventService.trackEvent(EventType.LOGIN,
                user, null);

        return new AuthResponse(token);
    }
}
