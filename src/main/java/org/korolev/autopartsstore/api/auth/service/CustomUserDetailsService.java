package org.korolev.autopartsstore.api.auth.service;

import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NullMarked;
import org.korolev.autopartsstore.api.admin.entity.AdminEntity;
import org.korolev.autopartsstore.api.admin.repository.AdminRepository;
import org.korolev.autopartsstore.api.customer.entity.CustomerEntity;
import org.korolev.autopartsstore.api.customer.repository.CustomerRepository;
import org.korolev.autopartsstore.api.role.entity.AdminRole;
import org.korolev.autopartsstore.api.role.entity.CustomerRole;
import org.korolev.autopartsstore.api.role.repository.AdminRoleRepository;
import org.korolev.autopartsstore.api.role.repository.CustomerRoleRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@NullMarked
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final AdminRepository adminRepository;
    private final AdminRoleRepository adminRoleRepository;
    private final CustomerRepository customerRepository;
    private final CustomerRoleRepository customerRoleRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<AdminEntity> admin = adminRepository.findByEmail(email);

        if(admin.isPresent()) {
            List<AdminRole> adminRoles = adminRoleRepository.findById_AdminId((admin.get().getId()));
            List<SimpleGrantedAuthority> authorities = adminRoles.stream()
                    .map(ar -> new SimpleGrantedAuthority(ar.getRole().getRoleCode()))
                    .toList();


            return User.builder()
                    .username(admin.get().getEmail())
                    .password(admin.get().getPasswordHash())
                    .authorities(authorities)
                    .build();
        }

        Optional<CustomerEntity> customer = customerRepository.findByEmail(email);
        if(customer.isPresent()) {
            List<CustomerRole> customerRoles = customerRoleRepository.findById_CustomerId((customer.get().getId()));
            List<SimpleGrantedAuthority> authorities = customerRoles.stream()
                    .map(cr -> new SimpleGrantedAuthority(cr.getRole().getRoleCode()))
                    .toList();

            return User.builder()
                    .username(customer.get().getEmail())
                    .password(customer.get().getPasswordHash())
                    .authorities(authorities)
                    .build();
        }

        throw new UsernameNotFoundException("User with email " + email + " not found");
     }
}
