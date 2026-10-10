package org.korolev.autopartsstore.api.customer.service;

import lombok.AllArgsConstructor;
import org.korolev.autopartsstore.api.customer.dto.CustomerRequest;
import org.korolev.autopartsstore.api.customer.dto.CustomerResponse;
import org.korolev.autopartsstore.api.customer.entity.CustomerEntity;
import org.korolev.autopartsstore.api.customer.exception.CustomerAlreadyExistsException;
import org.korolev.autopartsstore.api.customer.exception.CustomerNotFoundException;
import org.korolev.autopartsstore.api.customer.mapper.CustomerMapper;
import org.korolev.autopartsstore.api.customer.repository.CustomerRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    @Transactional(readOnly = true)
    @PreAuthorize("hasRole('ADMIN')")
    public List<CustomerResponse> findAllCustomers() {
        return customerRepository.findAll().stream()
                .map(customerMapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    @PreAuthorize("hasRole('ADMIN')")
    public CustomerResponse findCustomerById(Long id) {
        return customerMapper.toResponse(
                customerRepository.findById(id)
                        .orElseThrow(
                        () -> new CustomerNotFoundException("Customer not found.")
        ));
    }

    @Transactional(readOnly = true)
    public CustomerEntity findByEmail(String email) {
        return customerRepository.findByEmail(email)
                        .orElseThrow(
                                () -> new CustomerNotFoundException("Customer with email" +
                                        email + " not found")
                        );
    }

    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public CustomerResponse updateCustomer(Long id, CustomerRequest customerRequest) {

        CustomerEntity customerEntity = customerRepository.findById(id).orElseThrow(
            () -> new CustomerNotFoundException("Customer not found.")
        );

        customerMapper.updateEntity(customerRequest, customerEntity);

        return customerMapper.toResponse(customerRepository.save(customerEntity));
    }

    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteCustomer(Long id) {
        if(!customerRepository.existsById(id)) {
            throw new CustomerNotFoundException("Customer not found.");
        }
        customerRepository.deleteById(id);
    }
}
