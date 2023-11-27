package com.sample.meliorapp.restController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.sample.meliorapp.ApplicationTestConfig;
import com.sample.meliorapp.Mapper.CustomerMapper;
import com.sample.meliorapp.Mapper.OrderMapper;
import com.sample.meliorapp.rest.advice.ExceptionControllerAdvice;
import com.sample.meliorapp.rest.controller.CustomerController;
import com.sample.meliorapp.rest.dto.CustomerDto;
import com.sample.meliorapp.rest.dto.FragranceTypeDto;
import com.sample.meliorapp.rest.dto.OrderDto;
import com.sample.meliorapp.rest.service.MeliorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@ContextConfiguration(classes = ApplicationTestConfig.class)
@WebAppConfiguration
public class CustomerControllerTests {
    @Autowired
    private CustomerController customerController;
    @Autowired
    private CustomerMapper customerMapper;
    @Autowired
    private OrderMapper orderMapper;

    @MockBean
    private MeliorService meliorService;

    private MockMvc mockMvc;
    private List<CustomerDto> customers;
    private List<OrderDto> orders;
    // Instantiate Mock Data
    @BeforeEach
    void initCustomer() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(customerController)
                .setControllerAdvice(new ExceptionControllerAdvice())
                .alwaysDo(MockMvcResultHandlers.print())
                .build();
        //---------------Mock Customers---------------
        customers = new ArrayList<>();

        CustomerDto customerWithOrder = new CustomerDto();
        customers.add(customerWithOrder.id(1)
                                    .firstName("JeffOne")
                                    .lastName("MontayaOne")
                                    .address("101 SpringCreek St.")
                                    .city("Dallas")
                                    .telephone("100000001")
                                    .email("jmontOne@corpx.com")
                                    .addOrdersItem(generateTestOrder(customerWithOrder, 1, 5, LocalDate.of(2023, 12, 01))));
        CustomerDto customer = new CustomerDto();
        customers.add(customer.id(2)
                            .firstName("JeffTwo")
                            .lastName("MontayaTwo")
                            .address("102 SpringCreek St.")
                            .city("Austin")
                            .telephone("100000002")
                            .email("jmontTwo@corpx.com"));
        customer = new CustomerDto();
        customers.add(customer.id(3)
                            .firstName("JeffThree")
                            .lastName("MontayaThree")
                            .address("103 SpringCreek St.")
                            .city("Oregano")
                            .telephone("100000003")
                            .email("jmontThree@corpx.com"));
        customer = new CustomerDto();
        customers.add(customer.id(4)
                            .firstName("JeffFour")
                            .lastName("MontayaFour")
                            .address("104 SpringCreek St.")
                            .city("Barley")
                            .telephone("1000000004")
                            .email("jmontFour@corpx.com"));
        
        //---------------Mock Orders---------------
        // Mock fragrance type
        FragranceTypeDto fragranceType = new FragranceTypeDto();
        fragranceType.id(2).name("lavender");

        // Mock orders
        orders = new ArrayList<>();

        OrderDto order = new OrderDto();
        orders.add(order.id(3)
                        .quantity(10)
                        .creationDate(LocalDate.of(2023,12,01))
                        .fragranceType(fragranceType));
        order = new OrderDto();
        orders.add(order.id(4)
                        .quantity(15)
                        .fragranceType(fragranceType));
    }

    private OrderDto generateTestOrder(final CustomerDto customer,
                                       final int id,
                                       final int quantity,
                                       final LocalDate creationDate) {
        FragranceTypeDto fragranceType = new FragranceTypeDto();
        OrderDto order = new OrderDto();
        order.id(id)
            .customerId(customer.getId())
            .quantity(quantity)
            .creationDate(creationDate)
            .fragranceType(fragranceType.id(2).name("lavender"));
        return order;
    }
    //=======================CUSTOMERS TESTS=======================
    //---------------GET---------------
    //GET SINGLE    api/customers/{id}
    @Test   // Success
    void testGetCustomerSuccess() throws Exception {
        given(this.meliorService.findCustomerById(1)).willReturn(customerMapper.toCustomer(customers.get(0)));
        this.mockMvc.perform(get("/api/customers/1")
                                .accept(MediaType.APPLICATION_JSON_VALUE))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType("application/json"))
                    .andExpect(jsonPath("$.id").value(1))
                    .andExpect(jsonPath("$.firstName").value("JeffOne"));
    }
    @Test   // Not found
    void testGetCustomerNotFound() throws Exception {
        given(this.meliorService.findCustomerById(2)).willReturn(null);
        this.mockMvc.perform(get("/api/customers/2")
                                .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isNotFound());
    }
    // GET LIST     api/customers
    @Test   // Success
    void testGetCustomerListSuccess() throws Exception {
        given(this.meliorService.findAllCustomer()).willReturn(customerMapper.toCustomers(customers));
        this.mockMvc.perform(get("/api/customers")
                                .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType("application/json"))
                    .andExpect(jsonPath("$.[0].id").value(1))
                    .andExpect(jsonPath("$.[0].firstName").value("JeffOne"))
                    .andExpect(jsonPath("$.[1].id").value(2))
                    .andExpect(jsonPath("$.[1].firstName").value("JeffTwo"))
                    .andExpect(jsonPath("$.[2].id").value(3))
                    .andExpect(jsonPath("$.[2].firstName").value("JeffThree"))
                    .andExpect(jsonPath("$.[3].id").value(4))
                    .andExpect(jsonPath("$.[3].firstName").value("JeffFour"));
    }
    @Test   // Not found
    void testGetCustomerListNotFound() throws Exception {
        customers.clear();
        given(this.meliorService.findAllCustomer()).willReturn(customerMapper.toCustomers(customers));
        this.mockMvc.perform(get("/api/customers")
                                .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isNotFound());
    }
    //---------------POST---------------
    //  api/customers
    @Test   // Success
    void testCreateCustomerSuccess() throws Exception {
        CustomerDto newCustomerDto = customers.get(0);
        newCustomerDto.setId(null);

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        String newCustomerAsJSON = mapper.writeValueAsString(newCustomerDto);

        this.mockMvc.perform(post("/api/customers")
                                .content(newCustomerAsJSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isCreated());
    }
    @Test   // Bad Request
    void testCreateCustomerError() throws Exception {
        CustomerDto newCustomerDto = customers.get(0);
        newCustomerDto.setId(null);
        newCustomerDto.setFirstName(null);

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        String newCustomerAsJSON = mapper.writeValueAsString(newCustomerDto);

        this.mockMvc.perform(post("/api/customers")
                                .content(newCustomerAsJSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isBadRequest());
    }
    //---------------PUT---------------
    // api/customers{id}
    @Test   // Success
    void testUpdateCustomerSuccess() throws Exception {
        given(this.meliorService.findCustomerById(1)).willReturn(customerMapper.toCustomer(customers.get(0)));
        CustomerDto updateCustomerDto = customers.get(0);

        updateCustomerDto.setFirstName("updatedJeffOne");
        updateCustomerDto.setLastName("updatedMontayaOne");

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        String updateCustomerAsJSON = mapper.writeValueAsString(updateCustomerDto);

        this.mockMvc.perform(put("/api/customers/1")
                        .content(updateCustomerAsJSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        this.mockMvc.perform(get("/api/customers/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.firstName").value("updatedJeffOne"))
                .andExpect(jsonPath("$.lastName").value("updatedMontayaOne"));
    }
    @Test   // Success with null Id in request body
    void testUpdateCustomerNoBodyIdSuccess() throws Exception {
        given(this.meliorService.findCustomerById(1)).willReturn(customerMapper.toCustomer(customers.get(0)));
        CustomerDto updateCustomerDto = customers.get(0);

        updateCustomerDto.setId(null);
        updateCustomerDto.setFirstName("updatedJeffOne");
        updateCustomerDto.setLastName("updatedMontayaOne");

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        String updateCustomerAsJSON = mapper.writeValueAsString(updateCustomerDto);

        this.mockMvc.perform(put("/api/customers/1")
                        .content(updateCustomerAsJSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        this.mockMvc.perform(get("/api/customers/1")
                                .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType("application/json"))
                    .andExpect(jsonPath("$.id").value(1))
                    .andExpect(jsonPath("$.firstName").value("updatedJeffOne"))
                    .andExpect(jsonPath("$.lastName").value("updatedMontayaOne"));
    }
    @Test   // Not Found
    void testUpdateCustomerNotFound() throws Exception{
        CustomerDto updateCustomerDto = customers.get(0);

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        String updateCustomerAsJSON = mapper.writeValueAsString(updateCustomerDto);

        this.mockMvc.perform(put("/api/customers/100")
                        .content(updateCustomerAsJSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
    @Test   // Bad Request
    void testUpdateCustomerError() throws Exception{
        CustomerDto updateCustomerDto = customers.get(0);
        updateCustomerDto.setLastName(null);

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        String updateCustomerAsJSON = mapper.writeValueAsString(updateCustomerDto);

        this.mockMvc.perform(put("/api/customers/1")
                        .content(updateCustomerAsJSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
    
    //---------------DELETE---------------
    // api/customers/{id}
    @Test   // Success
    void testDeleteCustomerSuccess() throws Exception {
        given(this.meliorService.findCustomerById(1)).willReturn(customerMapper.toCustomer(customers.get(0)));
        this.mockMvc.perform(delete("/api/customers/1")
                        .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isNoContent());
    }
    @Test   // Not Found
    void testDeleteCustomerError() throws Exception {
        given(this.meliorService.findCustomerById(100)).willReturn(null);
        this.mockMvc.perform(delete("/api/customers/100")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    //=======================ORDERS RELATED TESTS=======================
    //---------------POST---------------
    //  api/customers/{customerId}/orders
    @Test   // Success
    void testCreateCustomersOrderSuccess() throws Exception {
        given(this.meliorService.findCustomerById(2)).willReturn(this.customerMapper.toCustomer(customers.get(1)));
        OrderDto newOrderDto = orders.get(0);
        newOrderDto.setId(null);

        ObjectMapper objectMapper = new ObjectMapper();
        String newOrderAsJSON = objectMapper.writeValueAsString(newOrderDto);

        this.mockMvc.perform(post("/api/customers/2/orders")
                                .content(newOrderAsJSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isCreated());
    }

    @Test   // Bad Request
    void testCreatCustomerOrderError() throws Exception {
        OrderDto newOrderDto = orders.get(0);
        newOrderDto.setId(null);
        newOrderDto.setQuantity(null);

        ObjectMapper objectMapper = new ObjectMapper();
        String newOrderAsJSON = objectMapper.writeValueAsString(newOrderDto);

        this.mockMvc.perform(post("/api/customers/2/orders")
                        .content(newOrderAsJSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
}
