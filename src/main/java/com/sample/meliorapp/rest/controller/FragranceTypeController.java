package com.sample.meliorapp.rest.controller;

import com.sample.meliorapp.Mapper.FragranceTypeMapper;
import com.sample.meliorapp.model.FragranceType;
import com.sample.meliorapp.repository.FragranceRepository;
import com.sample.meliorapp.rest.api.FragrancetypesApi;
import com.sample.meliorapp.rest.dto.FragranceTypeDto;
import com.sample.meliorapp.rest.dto.FragranceTypeFieldsDto;
import com.sample.meliorapp.rest.service.MeliorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.NativeWebRequest;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(exposedHeaders = "errors, content-type")
@RequestMapping("/api")
public class FragranceTypeController implements FragrancetypesApi {
    private MeliorService meliorService;
    private FragranceTypeMapper fragranceTypeMapper;

    public FragranceTypeController(MeliorService meliorService, FragranceTypeMapper fragranceTypeMapper) {
        this.meliorService = meliorService;
        this.fragranceTypeMapper = fragranceTypeMapper;
    }

    @Override
    public ResponseEntity<FragranceTypeDto> addFragranceType(FragranceTypeFieldsDto fragranceTypeFieldsDto) {
        return FragrancetypesApi.super.addFragranceType(fragranceTypeFieldsDto);
    }

    @Override
    public ResponseEntity<Void> deleteFragranceType(Integer fragranceTypeId) {
        return FragrancetypesApi.super.deleteFragranceType(fragranceTypeId);
    }

    @Override
    public ResponseEntity<FragranceTypeDto> getFragranceType(Integer fragranceTypeId) {
        FragranceType fragranceType = this.meliorService.findFragranceById(fragranceTypeId);
        if (fragranceType != null)
            return ResponseEntity.ok(fragranceTypeMapper.toFragranceTypeDto(fragranceType));
        return ResponseEntity.notFound().build();
    }

    @Override
    public ResponseEntity<List<FragranceTypeDto>> listFragranceType() {
        return FragrancetypesApi.super.listFragranceType();
    }

    @Override
    public ResponseEntity<FragranceTypeDto> updateFragranceType(Integer fragranceTypeId, FragranceTypeFieldsDto fragranceTypeFieldsDto) {
        return FragrancetypesApi.super.updateFragranceType(fragranceTypeId, fragranceTypeFieldsDto);
    }
}
