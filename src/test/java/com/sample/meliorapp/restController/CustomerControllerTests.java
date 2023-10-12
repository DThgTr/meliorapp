package com.sample.meliorapp.restController;

import com.sample.meliorapp.Mapper.CustomerMapper;
import com.sample.meliorapp.Mapper.OrderMapper;
import com.sample.meliorapp.rest.advice.ExceptionControllerAdvice;
import com.sample.meliorapp.rest.controller.CustomerController;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes = TestConfiguration.class)
public class CustomerControllerTests {
    @Autowired
    private CustomerController customerController;
    @Autowired
    private CustomerMapper customerMapper;
    @Autowired
    private OrderMapper orderMapper;

    private MockMvc mockMvc;
    @BeforeEach
    void initCustomer() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(customerController)
                .setControllerAdvice(new ExceptionControllerAdvice())
                .build();

    }
    //=======================CUSTOMERS TESTS=======================
    // GET
        /*
        GET ALL:    list success
                    list not found
        GET SINGLE: get success
                    get not found
         */
    // POST
        /*
        POST:   create success
                create error
         */
    // PUT
        /*
        PUT:    update  success with bodyId
                        success no bodyId
                update  error
         */
    // DELETE
        /*
        DELETE: delete success
                delete error
         */

    //=======================ORDERS RELATED TESTS=======================
    // GET
        /*
        GET:    get order success
                get order error
         */
    // POST
        /*
        POST:   create order success
                create order error
         */
}
