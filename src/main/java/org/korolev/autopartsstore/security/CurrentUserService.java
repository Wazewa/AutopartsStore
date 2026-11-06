package org.korolev.autopartsstore.security;

import lombok.RequiredArgsConstructor;
import org.korolev.autopartsstore.user.entity.UserEntity;
import org.korolev.autopartsstore.user.service.UserService;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CurrentUserService {
    private final UserService userService;

    public String getEmail() {
        return getAuthentication().getName();
    }

    public UserEntity getCurrentUserOrNull() {
        Authentication auth = getAuthentication();

        if (auth == null || !auth.isAuthenticated() || auth instanceof AnonymousAuthenticationToken) {
            return null;
        }

        return userService.findByEmail(auth.getName());
    }

    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }
}