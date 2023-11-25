package com.sample.meliorapp.restController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sample.meliorapp.ApplicationTestConfig;
import com.sample.meliorapp.Mapper.OrderMapper;
import com.sample.meliorapp.rest.advice.ExceptionControllerAdvice;
import com.sample.meliorapp.rest.controller.OrderController;
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
public class OrderControllerTests {
    @Autowired
    OrderController orderController;
    @Autowired
    OrderMapper orderMapper;

    @MockBean
    MeliorService meliorService;
    MockMvc mockMvc;
    List<OrderDto>  orders;

    @BeforeEach
    void initOrder() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(orderController)
                .setControllerAdvice(new ExceptionControllerAdvice())
                .alwaysDo(MockMvcResultHandlers.print())
                .build();

        // Fragrance Type
        FragranceTypeDto fragrance = new FragranceTypeDto();
        fragrance.id(2).name("bougainvillea");

        //---------------Mock Order-------------
        orders = new ArrayList<>();

        OrderDto order = new OrderDto();
        orders.add(order.id(1)
                        .quantity(5)
                        .creationDate(LocalDate.of(2023, 12, 01))
                        .fragranceType(fragrance));
        order = new OrderDto();
        orders.add(order.id(2)
                        .quantity(10));
        order = new OrderDto();
        orders.add(order.id(3)
                        .quantity(15));
    }
    //---------------GET---------------
    // GET SINGLE: api/orders/{id}
    @Test   // Success
    void testGetOrderSuccess() throws Exception {
        given(this.meliorService.findOrderById(1)).willReturn(this.orderMapper.toOrder(orders.get(0)));
        this.mockMvc.perform(get("/api/orders/1")
                                .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType("application/json"))
                    .andExpect(jsonPath("$.id").value(1))
                    .andExpect(jsonPath("$.quantity").value("5"))
                    .andExpect(jsonPath("$.fragranceType.name").value("bougainvillea"))
                    // Creation date field test
                    .andExpect(jsonPath("$.creationDate.[0]").value(2023))
                    .andExpect(jsonPath("$.creationDate.[1]").value(12))
                    .andExpect(jsonPath("$.creationDate.[2]").value(01));
    }

    @Test   // Not Found
    void testGetOrderError() throws Exception {
        given(this.meliorService.findOrderById(1)).willReturn(null);
        this.mockMvc.perform(get("/api/orders/99")
                                .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isNotFound());
    }

    // GET ALL: api/orders
    @Test   // Success
    void testGetOrderListSuccess() throws Exception {
        given(this.meliorService.findAllOrder()).willReturn(orderMapper.toOrders(orders));
        this.mockMvc.perform(get("/api/orders")
                                .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType("application/json"))
                    .andExpect(jsonPath("$.[0].id").value(1))
                    .andExpect(jsonPath("$.[0].quantity").value("5"))
                    .andExpect(jsonPath("$.[0].fragranceType.name").value("bougainvillea"))
                    .andExpect(jsonPath("$.[1].id").value(2))
                    .andExpect(jsonPath("$.[1].quantity").value("10"))
                    .andExpect(jsonPath("$.[2].id").value(3))
                    .andExpect(jsonPath("$.[2].quantity").value("15"));
    }
    @Test   // Not Found
    void testGetOrderListError() throws Exception {
        orders.clear();
        given(this.meliorService.findAllOrder()).willReturn(orderMapper.toOrders(orders));
        this.mockMvc.perform(get("/api/orders")
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    //---------------PUT---------------
    //  api/orders/{id}
    @Test   // Success
    void testUpdateOrderSuccess() throws Exception {
        given(this.meliorService.findOrderById(1)).willReturn(this.orderMapper.toOrder(orders.get(0)));
        OrderDto updateOrderDto = orders.get(0);
        updateOrderDto.setQuantity(20);

        FragranceTypeDto updateFragrance = new FragranceTypeDto();
        updateFragrance.setId(3);
        updateFragrance.setName("lily");
        updateOrderDto.setFragranceType(updateFragrance);

        ObjectMapper mapper = new ObjectMapper();
        String updateOrderAsJSON = mapper.writeValueAsString(updateOrderDto);

        this.mockMvc.perform(put("/api/orders/1")
                        .content(updateOrderAsJSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        this.mockMvc.perform(get("/api/orders/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.quantity").value("20"))
                .andExpect(jsonPath("$.fragranceType.name").value("lily"));
    }

    @Test   // Not Found
    void testUpdateOrderError() throws Exception {
        OrderDto updateOrderDto = orders.get(0);

        ObjectMapper mapper = new ObjectMapper();
        String updateOrderAsJSON = mapper.writeValueAsString(updateOrderDto);

        this.mockMvc.perform(put("/api/orders/100")
                        .content(updateOrderAsJSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    //---------------DELETE---------------
    //  api/orders/{id}
    @Test   // Success
    void testDeleteOrderSuccess() throws Exception {
        given(this.meliorService.findOrderById(1)).willReturn(orderMapper.toOrder(orders.get(0)));
        this.mockMvc.perform(delete("/api/orders/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test   // Not Found
    void testDeleteOrderError() throws Exception {
        given(this.meliorService.findCustomerById(100)).willReturn(null);
        this.mockMvc.perform(delete("/api/orders/100")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
