package org.korolev.automagazine.api.services;

import lombok.AllArgsConstructor;
import org.korolev.automagazine.api.dto.CustomerRequest;
import org.korolev.automagazine.api.dto.CustomerResponse;
import org.korolev.automagazine.api.entities.CustomerEntity;
import org.korolev.automagazine.api.exceptions.CustomerAlreadyExistsException;
import org.korolev.automagazine.api.exceptions.CustomerNotFoundException;
import org.korolev.automagazine.api.mappers.CustomerMapper;
import org.korolev.automagazine.api.repositories.CustomerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    @Transactional(readOnly = true)
    public List<CustomerResponse> findAllCustomers() {
        return customerRepository.findAll().stream()
                .map(customerMapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public CustomerResponse findCustomerById(Long id) {
        return customerMapper.toResponse(
                customerRepository.findById(id)
                        .orElseThrow(
                        () -> new CustomerNotFoundException("Customer not found.")
        ));
    }

    @Transactional
    public CustomerResponse createCustomer(CustomerRequest customerRequest) {

        CustomerEntity customerEntity = customerMapper.toEntity(customerRequest);

        if (customerRepository.existsByEmail(customerEntity.getEmail())) {
            throw new CustomerAlreadyExistsException("Customer with " + customerEntity.getEmail() + " email already exists.");
        }
        return customerMapper.toResponse(customerRepository.save(customerEntity));
    }

    @Transactional
    public CustomerResponse updateCustomer(Long id, CustomerRequest customerRequest) {

        CustomerEntity customerEntity = customerRepository.findById(id).orElseThrow(
            () -> new CustomerNotFoundException("Customer not found.")
        );

        customerMapper.updateEntity(customerRequest, customerEntity);

        return customerMapper.toResponse(customerRepository.save(customerEntity));
    }

    @Transactional
    public void deleteCustomer(Long id) {
        if(!customerRepository.existsById(id)) {
            throw new CustomerNotFoundException("Customer not found.");
        }
        customerRepository.deleteById(id);
    }

}
