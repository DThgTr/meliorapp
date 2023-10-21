package com.sample.meliorapp.rest.service;

import com.sample.meliorapp.model.Customer;
import com.sample.meliorapp.model.FragranceType;
import com.sample.meliorapp.repository.CustomerRepository;
import com.sample.meliorapp.repository.FragranceRepository;
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

    @Autowired
    private FragranceRepository fragranceRepository;

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

    //----------------Fragrance Type related Operation----------------

    @Override
    public FragranceType findFragranceById(int id) {
        return fragranceRepository.findById(id);
    }

    @Override
    public Collection<FragranceType> findFragranceByName(String name) {
        return fragranceRepository.findByName(name);
    }

    @Override
    public Collection<FragranceType> findAllFragrance() {
        return fragranceRepository.findAll();
    }

    @Override
    public void saveFragranceType(FragranceType fragranceType) {
        fragranceRepository.save(fragranceType);
    }

    @Override
    public void deleteFragranceType(FragranceType fragranceType) {
        fragranceRepository.deleteById(fragranceType.getId());
    }
}
