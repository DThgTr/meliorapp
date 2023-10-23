package com.sample.meliorapp.restController;

import com.fasterxml.jackson.databind.ObjectMapper;
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
                                .name("erdleafFlower"));

        fragrance = new FragranceTypeDto();
        fragrances.add(fragrance.id(2)
                                .name("rowaFruit"));

        fragrance = new FragranceTypeDto();
        fragrances.add(fragrance.id(3)
                                .name("dewkissedHerba"));

        fragrance = new FragranceTypeDto();
        fragrances.add(fragrance.id(4)
                                .name("altusBloom"));
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
                    .andExpect(jsonPath("$.name").value("erdleafFlower"));
    }

    @Test   // Not Found
    void testGetFragranceTypeError() throws Exception {
        given(this.meliorService.findFragranceById(3)).willReturn(null);
        this.mockMvc.perform(get("/api/fragrancetypes/3")
                               .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isNotFound());
    }
    // GET ALL      api/fragrancetypes
    @Test   // Success
    void testGetFragranceTypeListSuccess() throws Exception {
        given(this.meliorService.findAllFragrance()).willReturn(fragranceTypeMapper.toFragranceTypes(fragrances));
        this.mockMvc.perform(get("/api/fragrancetypes")
                                .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType("application/json"))
                    .andExpect(jsonPath("$.[0].id").value(1))
                    .andExpect(jsonPath("$.[0].name").value("erdleafFlower"))
                    .andExpect(jsonPath("$.[1].id").value(2))
                    .andExpect(jsonPath("$.[1].name").value("rowaFruit"))
                    .andExpect(jsonPath("$.[2].id").value(3))
                    .andExpect(jsonPath("$.[2].name").value("dewkissedHerba"))
                    .andExpect(jsonPath("$.[3].id").value(4))
                    .andExpect(jsonPath("$.[3].name").value("altusBloom"));
    }

    @Test   // Not Found
    void testGetFragranceTypeListError() throws Exception {
        fragrances.clear();
        given(this.meliorService.findAllFragrance()).willReturn(this.fragranceTypeMapper.toFragranceTypes(fragrances));
        this.mockMvc.perform(get("/api/fragrancetypes")
                                .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isNotFound());
    }

    //---------------POST---------------
    //  api/fragrancetypes
    @Test   // Success
    void testCreateFragranceTypeSuccess() throws Exception {
        FragranceTypeDto newFragranceTypeDto = fragrances.get(0);
        newFragranceTypeDto.setId(null);

        ObjectMapper mapper = new ObjectMapper();
        String newFragranceTypeAsJSON = mapper.writeValueAsString(newFragranceTypeDto);

        this.mockMvc.perform(post("/api/fragrancetypes")
                        .content(newFragranceTypeAsJSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test   // Bad Request
    void testCreateFragranceTypeError() throws Exception {
        FragranceTypeDto newFragranceTypeDto = fragrances.get(0);
        newFragranceTypeDto.setId(null);
        newFragranceTypeDto.setName(null);

        ObjectMapper mapper = new ObjectMapper();
        String newFragranceTypeAsJSON = mapper.writeValueAsString(newFragranceTypeDto);

        this.mockMvc.perform(post("/api/fragrancetypes")
                        .content(newFragranceTypeAsJSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    //---------------PUT---------------
    //  api/fragrancetypes/{id}
    @Test   // Success
    void testUpdateFragranceTypeSuccess() throws Exception {
        given(this.meliorService.findFragranceById(1)).willReturn(fragranceTypeMapper.toFragranceType(fragrances.get(0)));
        FragranceTypeDto updateFragranceTypeDto = fragrances.get(0);

        updateFragranceTypeDto.setName("fadedErdleafFlower");

        ObjectMapper mapper = new ObjectMapper();
        String updateFragranceTypeAsJSON = mapper.writeValueAsString(updateFragranceTypeDto);

        this.mockMvc.perform(put("/api/fragrancetypes/1")
                        .content(updateFragranceTypeAsJSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        this.mockMvc.perform(get("/api/fragrancetypes/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("fadedErdleafFlower"));
    }

    @Test   // Success with null BodyId in request body
    void testUpdateFragranceTypeNoBodyIdSuccess() throws Exception {
        given(this.meliorService.findFragranceById(1)).willReturn(fragranceTypeMapper.toFragranceType(fragrances.get(0)));
        FragranceTypeDto updateFragranceTypeDto = fragrances.get(0);

        updateFragranceTypeDto.setId(null);
        updateFragranceTypeDto.setName("fadedErdleafFlower");

        ObjectMapper mapper = new ObjectMapper();
        String updateFragranceTypeAsJSON = mapper.writeValueAsString(updateFragranceTypeDto);

        this.mockMvc.perform(put("/api/fragrancetypes/1")
                        .content(updateFragranceTypeAsJSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        this.mockMvc.perform(get("/api/fragrancetypes/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("fadedErdleafFlower"));
    }

    @Test   // Bad Request
    void testUpdateFragranceTypeError() throws Exception {
        FragranceTypeDto updateFragranceTypeDto = fragrances.get(0);
        updateFragranceTypeDto.setName(null);

        ObjectMapper mapper = new ObjectMapper();
        String updateFragranceTypeAsJSON = mapper.writeValueAsString(updateFragranceTypeDto);

        this.mockMvc.perform(put("/api/fragrancetypes/1")
                        .content(updateFragranceTypeAsJSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    //---------------DELETE---------------
    //  api/fragrancetypes/{id}
    @Test   // Success
    void testDeleteFragranceTypeSuccess() throws Exception {
        given(this.meliorService.findFragranceById(1)).willReturn(this.fragranceTypeMapper.toFragranceType(fragrances.get(0)));
        this.mockMvc.perform(delete("/api/fragrancetypes/1")
                                .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isNoContent());
    }

    @Test   // Not FOund
    void testDeleteFragranceTypeError() throws Exception {
        given(this.meliorService.findFragranceById(100)).willReturn(null);
        this.mockMvc.perform(delete("/api/fragrancetypes/100")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
