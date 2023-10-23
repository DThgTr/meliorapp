package com.sample.meliorapp.rest.service;

import com.sample.meliorapp.model.Customer;
import com.sample.meliorapp.model.FragranceType;
import com.sample.meliorapp.model.Order;
import com.sample.meliorapp.repository.CustomerRepository;
import com.sample.meliorapp.repository.FragranceRepository;
import com.sample.meliorapp.repository.OrderRepository;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.NoSuchElementException;

@Service
public class MeliorServiceImpl implements MeliorService {
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private FragranceRepository fragranceRepository;
    @Autowired
    private OrderRepository orderRepository;

    //----------------Customer related Operation----------------
    @Override
    //@Transactional(readOnly = true)
    public Customer findCustomerById(int id) {
        try {
            return customerRepository.findById(id).get();
        }
        catch (NoSuchElementException e) {
            return null;
        }
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
        customerRepository.delete(customer);
    }

    //----------------Order related Operation----------------
    @Override
    public Order findOrderById(int id) {
        try {
            return orderRepository.findById(id).get();
        }
        catch (NoSuchElementException e) {
            return null;
        }
    }

    @Override
    public Collection<Order> findAllOrder() {
        return orderRepository.findAll();
    }

    @Override
    public void saveOrder(Order order) {
        orderRepository.save(order);
    }

    @Override
    public void deleteOrder(Order order) {
        orderRepository.delete(order);
    }

    //----------------Fragrance Type related Operation----------------
    @Override
    public FragranceType findFragranceById(int id) {
        //return fragranceRepository.findById(id).get();
        try {
            return fragranceRepository.findById(id).get();
        }
        catch (NoSuchElementException e) {
            return null;
        }
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
        fragranceRepository.deleteRelatedOrdersByFragranceType(fragranceType);
        fragranceRepository.delete(fragranceType);
    }
}
