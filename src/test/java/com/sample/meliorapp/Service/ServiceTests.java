package com.sample.meliorapp.Service;

import com.sample.meliorapp.model.Customer;
import com.sample.meliorapp.model.FragranceType;
import com.sample.meliorapp.rest.service.MeliorService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("hsqldb")
public class ServiceTests {
    @Autowired
    MeliorService meliorService;

    //----------------------------CUSTOMER-RELATED----------------------------
    @Test
    void shouldFindCustomersByLastName() {
        Collection<Customer> customers = this.meliorService.findCustomerByLastName("Yatamon");
        assertThat(customers.size()).isEqualTo(3);

        customers = this.meliorService.findCustomerByLastName("JeffFaker");
        assertThat(customers.isEmpty()).isTrue();
    }

    @Test
    void shouldFindSingleCustomerWithOrder() {
        Customer customer = this.meliorService.findCustomerById(1);
        assertThat(customer).isNotNull();
        assertThat(customer.getLastName()).isEqualTo("MontayaOne");
        assertThat(customer.getOrders().size()).isEqualTo(1);
        assertThat(customer.getOrders().get(0).getQuantity()).isEqualTo(5);
        assertThat(customer.getOrders().get(0).getFragranceType()).isNotNull();
        assertThat(customer.getOrders().get(0).getFragranceType().getName()).isEqualTo("lavender");
    }
    @Test
    void shouldFindAllCustomer() {
        Collection<Customer> customers = this.meliorService.findAllCustomer();
        List<Customer> customerList = new ArrayList<>(customers);
        assertThat(customerList.size()).isEqualTo(10);

        assertThat(customerList.get(0).getFirstName()).isEqualTo("JeffOne");
        assertThat(customerList.get(0).getLastName()).isEqualTo("MontayaOne");

        assertThat(customerList.get(2).getFirstName()).isEqualTo("JeffThree");
        assertThat(customerList.get(2).getLastName()).isEqualTo("MontayaThree");

        assertThat(customerList.get(4).getFirstName()).isEqualTo("JeffFive");
        assertThat(customerList.get(4).getLastName()).isEqualTo("MontayaFive");

        assertThat(customerList.get(9).getFirstName()).isEqualTo("Het");
        assertThat(customerList.get(9).getLastName()).isEqualTo("Yatamon");
    }

    @Test
    void shouldSaveNewCustomer() {
        Customer customer = new Customer();
        customer.setFirstName("JeffNew");
        customer.setLastName("MontayaNew");
        customer.setAddress("201 NewCreek St.");
        customer.setCity("New Reno");
        customer.setTelephone("200000001");
        customer.setEmail("mailnew@corpx.com");

        meliorService.saveCustomer(customer);
        assertThat(customer.getId()).isNotEqualTo(0);

        Collection<Customer> newCustomerColWrap = this.meliorService.findCustomerByLastName("MontayaNew");
        List<Customer> newCustomerListWrap = new ArrayList<>(newCustomerColWrap);
        assertThat(newCustomerListWrap.get(0).getFirstName()).isEqualTo("JeffNew");
        assertThat(newCustomerListWrap.get(0).getLastName()).isEqualTo("MontayaNew");
    }

    @Test
    void shouldDeleteCustomer() {
        Customer customer = this.meliorService.findCustomerById(1);
        this.meliorService.deleteCustomer(customer);

        customer = this.meliorService.findCustomerById(1);
        assertThat(customer).isNull();
    }

    //----------------------------ORDER-RELATED----------------------------


    //----------------------------FRAGRANCE-RELATED----------------------------
}
