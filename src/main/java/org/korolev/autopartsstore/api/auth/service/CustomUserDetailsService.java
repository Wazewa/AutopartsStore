package org.korolev.autopartsstore.api.auth;

import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NullMarked;
import org.korolev.autopartsstore.api.admin.entity.AdminEntity;
import org.korolev.autopartsstore.api.admin.repository.AdminRepository;
import org.korolev.autopartsstore.api.customer.entity.CustomerEntity;
import org.korolev.autopartsstore.api.customer.repository.CustomerRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final AdminRepository adminRepository;
    private final CustomerRepository customerRepository;

    @Override
    @NullMarked
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<AdminEntity> admin = adminRepository.findByEmail(email);
        if(admin.isPresent()) {
            return User.builder()
                    .username(admin.get().getEmail())
                    .password(admin.get().getPasswordHash())
                    .authorities(new SimpleGrantedAuthority("ROLE_ADMIN"))
                    .build();
        }

        Optional<CustomerEntity> customer = customerRepository.findByEmail(email);
        if(customer.isPresent()) {
            return User.builder()
                    .username(customer.get().getEmail())
                    .password(customer.get().getPasswordHash())
                    .authorities(new SimpleGrantedAuthority("ROLE_CUSTOMER"))
                    .build();
        }

        throw new UsernameNotFoundException("User with email " + email + " not found");
     }
}
