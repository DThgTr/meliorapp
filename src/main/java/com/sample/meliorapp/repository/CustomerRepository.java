package com.sample.meliorapp.repository;

import com.sample.meliorapp.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {

    @Query("SELECT DISTINCT customer FROM Customer customer left join fetch customer.orders WHERE customer.lastName LIKE :lastName%")
    Collection<Customer> findByLastName(@Param("lastName") String lastName);

    @Query("SELECT customer FROM Customer customer left join fetch customer.orders WHERE customer.id =:id")
    Customer findById(@Param("id") int id);
}
