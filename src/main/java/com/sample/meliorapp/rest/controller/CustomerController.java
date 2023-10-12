package com.sample.meliorapp.rest.controller;

import com.sample.meliorapp.rest.api.CustomersApi;
import com.sample.meliorapp.rest.dto.CustomerDto;
import com.sample.meliorapp.rest.dto.CustomerFieldsDto;
import com.sample.meliorapp.rest.dto.OrderDto;
import com.sample.meliorapp.rest.dto.OrderFieldsDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(exposedHeaders = "errors, content-type")
@RequestMapping("/api")
public class CustomerController implements CustomersApi {
    //------------------------Customers-related------------------------
    //GET
    @Override   // ALL
    public ResponseEntity<List<CustomerDto>> listCustomers() {
        return CustomersApi.super.listCustomers();
    }
    @Override   // SINGLE
    public ResponseEntity<CustomerDto> getCustomer(Integer customerId) {
        return CustomersApi.super.getCustomer(customerId);
    }
    //POST
    @Override
    public ResponseEntity<CustomerDto> addCustomer(CustomerFieldsDto customerFieldsDto) {
        return CustomersApi.super.addCustomer(customerFieldsDto);
    }
    //PUT
    @Override
    public ResponseEntity<CustomerDto> updateCustomer(Integer customerId, CustomerFieldsDto customerFieldsDto) {
        return CustomersApi.super.updateCustomer(customerId, customerFieldsDto);
    }
    //DELETE
    @Override
    public ResponseEntity<Void> deleteCustomer(Integer customerId) {
        return CustomersApi.super.deleteCustomer(customerId);
    }

    //------------------------Orders-related------------------------
    //GET
    @Override
    public ResponseEntity<OrderDto> getCustomersOrder(Integer customerId, Integer orderId) {
        return CustomersApi.super.getCustomersOrder(customerId, orderId);
    }
    //POST
    @Override
    public ResponseEntity<OrderDto> addCustomersOrder(Integer customerId, OrderFieldsDto orderFieldsDto) {
        return CustomersApi.super.addCustomersOrder(customerId, orderFieldsDto);
    }
}
