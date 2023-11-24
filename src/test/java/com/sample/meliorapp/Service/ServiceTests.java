package com.sample.meliorapp.Service;

import com.sample.meliorapp.model.Customer;
import com.sample.meliorapp.model.FragranceType;
import com.sample.meliorapp.model.Order;
import com.sample.meliorapp.rest.service.MeliorService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
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
    void shouldFindCustomerByIdAndWithOrder() {
        Customer customer = this.meliorService.findCustomerById(1);
        assertThat(customer).isNotNull();
        assertThat(customer.getLastName()).isEqualTo("MontayaOne");
        assertThat(customer.getOrders().size()).isEqualTo(1);
        assertThat(customer.getOrders().get(0).getQuantity()).isEqualTo(5);
        assertThat(customer.getOrders().get(0).getDate().toString()).isEqualTo("2023-01-01");
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
    @DirtiesContext
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
    @DirtiesContext
    void shouldUpdateCustomer() {
        Customer customer = this.meliorService.findCustomerById(1);
        customer.setFirstName("UpdatedJeffOne");
        customer.setLastName("UpdatedMontayaOne");

        this.meliorService.saveCustomer(customer);

        customer = this.meliorService.findCustomerById(1);
        assertThat(customer.getFirstName()).isEqualTo("UpdatedJeffOne");
        assertThat(customer.getLastName()).isEqualTo("UpdatedMontayaOne");
    }

    @Test
    @DirtiesContext
    void shouldDeleteCustomer() {
        Customer customer = this.meliorService.findCustomerById(1);
        this.meliorService.deleteCustomer(customer);

        customer = this.meliorService.findCustomerById(1);
        assertThat(customer).isNull();
    }

    //----------------------------ORDER-RELATED----------------------------
    @Test
    void shouldFindOrderById() {
        Order order = this.meliorService.findOrderById(1);
        assertThat(order.getQuantity()).isEqualTo(5);
        assertThat(order.getDate().toString()).isEqualTo("2023-01-01");
        assertThat(order.getCustomer().getFirstName()).isEqualTo("JeffOne");
        assertThat(order.getCustomer().getLastName()).isEqualTo("MontayaOne");
    }
    @Test
    void shouldFindAllOrder() {
        Collection<Order> orders = this.meliorService.findAllOrder();
        assertThat(orders.size()).isEqualTo(13);

        List<Order> orderList = new ArrayList<>(orders);
        assertThat(orderList.get(0).getQuantity()).isEqualTo(5);
        assertThat(orderList.get(0).getDate().toString()).isEqualTo("2023-01-01");
        assertThat(orderList.get(0).getFragranceType().getName()).isEqualTo("lavender");
        assertThat(orderList.get(0).getCustomer().getFirstName()).isEqualTo("JeffOne");

        assertThat(orderList.get(2).getQuantity()).isEqualTo(3);
        assertThat(orderList.get(2).getDate().toString()).isEqualTo("2023-01-01");
        assertThat(orderList.get(2).getFragranceType().getName()).isEqualTo("rose");
        assertThat(orderList.get(2).getCustomer().getFirstName()).isEqualTo("JeffThree");

        assertThat(orderList.get(4).getQuantity()).isEqualTo(13);
        assertThat(orderList.get(4).getDate().toString()).isEqualTo("2023-01-04");
        assertThat(orderList.get(4).getFragranceType().getName()).isEqualTo("jasmine");
        assertThat(orderList.get(4).getCustomer().getFirstName()).isEqualTo("JeffFour");

        assertThat(orderList.get(12).getQuantity()).isEqualTo(2);
        assertThat(orderList.get(12).getDate().toString()).isEqualTo("2023-11-23");
        assertThat(orderList.get(12).getFragranceType().getName()).isEqualTo("lavender");
        assertThat(orderList.get(12).getCustomer().getFirstName()).isEqualTo("Het");
    }
    @Test
    @DirtiesContext
    void shouldAddOrderToCustomer() {
        Customer customer = this.meliorService.findCustomerById(5);
        int foundOrderNum = customer.getOrders().size();
        LocalDate localDate = LocalDate.now();

        Order order = new Order();
        order.setQuantity(12);
        order.setDate(localDate);
        order.setFragranceType(this.meliorService.findFragranceById(4));
        customer.addOrder(order);

        this.meliorService.saveOrder(order);
        this.meliorService.saveCustomer(customer);

        customer = this.meliorService.findCustomerById(5);
        List<Order> orderList = customer.getOrders();
        assertThat(orderList.size()).isEqualTo(foundOrderNum + 1);

        assertThat(order.getId()).isNotNull();
        order = this.meliorService.findOrderById(order.getId());
        assertThat(order.getQuantity()).isEqualTo(12);
        assertThat(order.getDate().toString()).isEqualTo(localDate.toString());
        assertThat(order.getCustomer().getFirstName()).isEqualTo("JeffFive");
        assertThat(order.getFragranceType().getName()).isEqualTo("citrusMild");
    }

    @Test
    @DirtiesContext
    void shouldUpdateOrder() {
        Order order = this.meliorService.findOrderById(10);
        order.setQuantity(20);

        this.meliorService.saveOrder(order);

        order = this.meliorService.findOrderById(10);
        assertThat(order.getQuantity()).isEqualTo(20);
    }

    @Test
    @DirtiesContext
    @Transactional
    void shouldDeleteOrder() {
        Order order = this.meliorService.findOrderById(1);
        this.meliorService.deleteOrder(order);

        order = this.meliorService.findOrderById(1);
        assertThat(order).isNull();
    }

    //----------------------------FRAGRANCE-RELATED----------------------------
    @Test
    void shouldFindFragranceById() {
        FragranceType fragranceType = this.meliorService.findFragranceById(3);
        assertThat(fragranceType.getName()).isEqualTo("jasmine");
    }
    @Test
    void shouldFindFragranceByName() {
        Collection<FragranceType> fragrances = this.meliorService.findFragranceByName("citrus");
        assertThat(fragrances.size()).isEqualTo(3);

        fragrances = this.meliorService.findFragranceByName("sailorOdor");
        assertThat(fragrances.isEmpty()).isTrue();
    }
    @Test
    void shouldFindAllFragrance() {
        Collection<FragranceType> fragrances = this.meliorService.findAllFragrance();
        List<FragranceType> fragrancesList = new ArrayList<>(fragrances);
        assertThat(fragrances.size()).isEqualTo(6);

        assertThat(fragrancesList.get(0).getName()).isEqualTo("lavender");
        assertThat(fragrancesList.get(1).getName()).isEqualTo("rose");
        assertThat(fragrancesList.get(2).getName()).isEqualTo("jasmine");
    }
    @Test
    @DirtiesContext
    void shouldSaveNewFragrance() {
        FragranceType fragrance = new FragranceType();
        fragrance.setName("bougainvillea");

        this.meliorService.saveFragranceType(fragrance);
        assertThat(fragrance.getId()).isNotEqualTo(0);

        Collection<FragranceType> newFragranceColWrap = this.meliorService.findFragranceByName("bougainvillea");
        List<FragranceType> newFragranceListWrap = new ArrayList<>(newFragranceColWrap);
        assertThat(newFragranceListWrap.get(0).getName()).isEqualTo("bougainvillea");
    }
    @Test
    @DirtiesContext
    void shouldUpdateFragrance() {
        FragranceType fragrance = this.meliorService.findFragranceById(1);
        fragrance.setName("updatedJasmine");

        this.meliorService.saveFragranceType(fragrance);

        fragrance = this.meliorService.findFragranceById(1);
        assertThat(fragrance.getName()).isEqualTo("updatedJasmine");
    }
    @Test
    void shouldDeleteFragrance() {
        FragranceType fragrance = this.meliorService.findFragranceById(1);
        this.meliorService.deleteFragranceType(fragrance);

        fragrance = this.meliorService.findFragranceById(1);
        assertThat(fragrance).isNull();
    }
}
