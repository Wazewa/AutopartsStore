package org.korolev.autopartsstore.api.user.service;

import lombok.AllArgsConstructor;
import org.korolev.autopartsstore.api.dto.PageResponse;
import org.korolev.autopartsstore.api.user.dto.UserRequest;
import org.korolev.autopartsstore.api.user.dto.UserResponse;
import org.korolev.autopartsstore.api.user.entity.UserEntity;
import org.korolev.autopartsstore.api.user.exception.UserNotFoundException;
import org.korolev.autopartsstore.api.user.mapper.UserMapper;
import org.korolev.autopartsstore.api.user.repository.UserRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Transactional(readOnly = true)
    @PreAuthorize("hasRole('ADMIN')")
    public PageResponse<UserResponse> findAllUsers(Pageable pageable) {
        return PageResponse.of(userRepository.findAll(pageable), userMapper::toResponse);
    }

    @Transactional(readOnly = true)
    @PreAuthorize("hasRole('ADMIN')")
    public UserResponse findUserById(Long id) {
        return userMapper.toResponse(
                userRepository.findById(id)
                        .orElseThrow(
                        () -> new UserNotFoundException("User not found.")
        ));
    }

    @Transactional(readOnly = true)
    public UserEntity findByEmail(String email) {
        return userRepository.findByEmail(email)
                        .orElseThrow(
                                () -> new UserNotFoundException("User with email " +
                                        email + " not found")
                        );
    }

    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteUser(Long id) {
        if(!userRepository.existsById(id)) {
            throw new UserNotFoundException("User not found.");
        }
        userRepository.deleteById(id);
    }
}
