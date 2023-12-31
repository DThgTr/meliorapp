package com.sample.meliorapp.rest.service;

import com.sample.meliorapp.model.Customer;
import com.sample.meliorapp.model.FragranceType;
import com.sample.meliorapp.model.Order;
import org.springframework.dao.DataAccessException;

import java.util.Collection;

/** Service Layer Interface
   * Provide a central access point for Melior controllers
*/
public interface MeliorService {
    //------------Customer------------
    Customer findCustomerById(int id);
    Collection<Customer> findAllCustomer();
    void saveCustomer(Customer customer);
    Collection<Customer> findCustomerByLastName(String lastName);
    void deleteCustomer(Customer customer);

    //------------Order------------
    Order findOrderById(int id);
    Collection<Order> findAllOrder();
    void saveOrder(Order order);
    void deleteOrder(Order order);

    //------------Fragrance------------
    FragranceType findFragranceById(int id);
    Collection<FragranceType> findFragranceByName(String name);
    Collection<FragranceType> findAllFragrance();
    void saveFragranceType(FragranceType fragranceType);
    void deleteFragranceType(FragranceType fragranceType);
}
