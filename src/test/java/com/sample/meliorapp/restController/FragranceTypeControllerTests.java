package com.sample.meliorapp.restController;

import com.sample.meliorapp.ApplicationTestConfig;
import com.sample.meliorapp.Mapper.FragranceTypeMapper;
import com.sample.meliorapp.rest.advice.ExceptionControllerAdvice;
import com.sample.meliorapp.rest.controller.FragranceTypeController;
import com.sample.meliorapp.rest.dto.FragranceTypeDto;
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

import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ContextConfiguration(classes = ApplicationTestConfig.class)
@WebAppConfiguration
public class FragranceTypeControllerTests {
    @Autowired
    FragranceTypeController fragranceTypeController;
    @Autowired
    FragranceTypeMapper fragranceTypeMapper;

    @MockBean
    MeliorService meliorService;

    private MockMvc mockMvc;
    private List<FragranceTypeDto> fragrances;

    @BeforeEach
    void initFragranceTypes() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(fragranceTypeController)
                .setControllerAdvice(new ExceptionControllerAdvice())
                .alwaysDo(MockMvcResultHandlers.print())
                .build();

        //-----------------MOCK Fragrance Type----------------
        fragrances = new ArrayList<FragranceTypeDto>();
        FragranceTypeDto fragrance = new FragranceTypeDto();

        fragrances.add(fragrance.id(1)
                                .name("erdleaf flower"));

        fragrance = new FragranceTypeDto();
        fragrances.add(fragrance.id(2)
                                .name("rowa fruit"));

        fragrance = new FragranceTypeDto();
        fragrances.add(fragrance.id(3)
                                .name("dewkissed herba"));

        fragrance = new FragranceTypeDto();
        fragrances.add(fragrance.id(4)
                                .name("altus bloom"));
    }

    //---------------GET---------------
    // GET SINGLE   api/fragrancetypes/{id}
    @Test   // Success
    void testGetFragranceTypeSuccess() throws Exception{
        given(this.meliorService.findFragranceById(1)).willReturn(fragranceTypeMapper.toFragranceType(fragrances.get(0)));
        this.mockMvc.perform(get("/api/fragrancetypes/1")
                                .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType("application/json"))
                    .andExpect(jsonPath("$.id").value(1))
                    .andExpect(jsonPath("$.name").value("erdleaf flower"));
    }

    @Test   // Not Found
    void testGetFragranceTypeError() throws Exception {
        given(this.meliorService.findFragranceById(3)).willReturn(null);
        this.mockMvc.perform(get("/api/fragrancetypes/3")
                               .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isNotFound());
    }
    // GET ALL      api/fragrancetypes

    //---------------POST---------------
    //---------------PUT---------------
    //---------------DELETE---------------
}
