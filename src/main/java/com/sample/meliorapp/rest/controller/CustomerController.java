package com.sample.meliorapp.rest.controller;

import com.sample.meliorapp.Mapper.CustomerMapper;
import com.sample.meliorapp.Mapper.OrderMapper;
import com.sample.meliorapp.model.Customer;
import com.sample.meliorapp.model.Order;
import com.sample.meliorapp.rest.api.CustomersApi;
import com.sample.meliorapp.rest.dto.CustomerDto;
import com.sample.meliorapp.rest.dto.CustomerFieldsDto;
import com.sample.meliorapp.rest.dto.OrderDto;
import com.sample.meliorapp.rest.dto.OrderFieldsDto;
import com.sample.meliorapp.rest.service.MeliorService;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Collection;
import java.util.List;

@RestController
@CrossOrigin(exposedHeaders = "errors, content-type")
@RequestMapping("/api")
public class CustomerController implements CustomersApi {
    private final MeliorService meliorService;
    private final CustomerMapper customerMapper;
    private final OrderMapper orderMapper;
    public CustomerController(MeliorService meliorService,
                                CustomerMapper customerMapper,
                                OrderMapper orderMapper) {
        this.meliorService = meliorService;
        this.customerMapper = customerMapper;
        this.orderMapper = orderMapper;
    }
    //------------------------Customers-related------------------------
    //GET
    @Override   // ALL
    public ResponseEntity<List<CustomerDto>> listCustomers() {
        Collection<Customer> customers = this.meliorService.findAllCustomer();
        if (!customers.isEmpty())
            return ResponseEntity.ok(customerMapper.toCustomerDtoCollection(customers));
        return ResponseEntity.notFound().build();
    }
    @Override   // SINGLE
    public ResponseEntity<CustomerDto> getCustomer(Integer customerId) {
        Customer customer = this.meliorService.findCustomerById(customerId);
        if (customer != null)
            return ResponseEntity.ok(customerMapper.toCustomerDto(customer));
        return ResponseEntity.notFound().build();
    }
    //POST
    @Override
    public ResponseEntity<CustomerDto> addCustomer(CustomerFieldsDto customerFieldsDto) {
        Customer newCustomer = customerMapper.toCustomer(customerFieldsDto);
        this.meliorService.saveCustomer(newCustomer);
        CustomerDto newCustomerDto = customerMapper.toCustomerDto(newCustomer);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(UriComponentsBuilder
                                .newInstance()
                                .path("/api/customers/{id}")
                                .buildAndExpand(newCustomer.getId())
                                .toUri());
        return new ResponseEntity<>(newCustomerDto, headers, HttpStatus.CREATED);
    }
    //PUT
    @Override
    public ResponseEntity<CustomerDto> updateCustomer(Integer customerId, CustomerFieldsDto customerFieldsDto) {
        Customer updateCustomer = this.meliorService.findCustomerById(customerId);
        if (updateCustomer == null)
            return ResponseEntity.notFound().build();

        updateCustomer.setFirstName(customerFieldsDto.getFirstName());
        updateCustomer.setLastName(customerFieldsDto.getLastName());
        updateCustomer.setAddress(customerFieldsDto.getAddress());
        updateCustomer.setCity(customerFieldsDto.getCity());
        updateCustomer.setTelephone(customerFieldsDto.getTelephone());
        updateCustomer.setEmail(customerFieldsDto.getEmail());

        this.meliorService.saveCustomer(updateCustomer);
        return new ResponseEntity<>(customerMapper.toCustomerDto(updateCustomer), HttpStatus.NO_CONTENT);
    }
    //DELETE
    @Override
    public ResponseEntity<Void> deleteCustomer(Integer customerId) {
        Customer customer = this.meliorService.findCustomerById(customerId);
        if (customer == null)
            return ResponseEntity.notFound().build();

        this.meliorService.deleteCustomer(customer);
        return ResponseEntity.noContent().build();
    }

    //------------------------Orders-related------------------------
    //POST
    @Override
    public ResponseEntity<OrderDto> addCustomersOrder(Integer customerId, OrderFieldsDto orderFieldsDto) {
        Order newOrder = orderMapper.toOrder(orderFieldsDto);

        Customer customer = this.meliorService.findCustomerById(customerId);
        customer.addOrder(newOrder);

        this.meliorService.saveOrder(newOrder);
        this.meliorService.saveCustomer(customer);

        OrderDto newOrderDto = this.orderMapper.toOrderDto(newOrder);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(UriComponentsBuilder
                                .newInstance()
                                .path("/api/orders/{id}")
                                .buildAndExpand(newOrder.getId())
                                .toUri());
        return new ResponseEntity<>(newOrderDto, headers, HttpStatus.CREATED);
    }
}
