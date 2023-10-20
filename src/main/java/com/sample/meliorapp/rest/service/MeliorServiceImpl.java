package com.sample.meliorapp.rest.service;

import com.sample.meliorapp.model.Customer;
import com.sample.meliorapp.repository.CustomerRepository;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Service
public class MeliorServiceImpl implements MeliorService {
    @Autowired
    private CustomerRepository customerRepository;

    /*
    public MeliorServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

     */

    //----------------Customer related Operation----------------
    @Override
    //@Transactional(readOnly = true)
    public Customer findCustomerById(int id) {
        return customerRepository.findById(id);
    }
    @Override
    //@Transactional(readOnly = true)
    public Collection<Customer> findAllCustomer() {
        return customerRepository.findAll();
    }
    @Override
    //@Transactional
    public void saveCustomer(Customer customer) {
        customerRepository.save(customer);
    }

    @Override
    @Transactional
    public Collection<Customer> findCustomerByLastName(String lastName) {
        return customerRepository.findByLastName(lastName);
    }

    @Override
    public void deleteCustomer(Customer customer) {
        customerRepository.deleteById(customer.getId());
    }
}
