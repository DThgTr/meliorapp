package com.sample.meliorapp.rest.controller;

import com.sample.meliorapp.Mapper.FragranceTypeMapper;
import com.sample.meliorapp.model.FragranceType;
import com.sample.meliorapp.model.FragranceType;
import com.sample.meliorapp.model.FragranceType;
import com.sample.meliorapp.repository.FragranceRepository;
import com.sample.meliorapp.rest.api.FragrancetypesApi;
import com.sample.meliorapp.rest.dto.FragranceTypeDto;
import com.sample.meliorapp.rest.dto.FragranceTypeDto;
import com.sample.meliorapp.rest.dto.FragranceTypeFieldsDto;
import com.sample.meliorapp.rest.service.MeliorService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Collection;
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

    // POST
    @Override   //  api/fragrancetypes
    public ResponseEntity<FragranceTypeDto> addFragranceType(FragranceTypeFieldsDto fragranceTypeFieldsDto) {
        FragranceType newFragranceType = fragranceTypeMapper.toFragranceType(fragranceTypeFieldsDto);
        this.meliorService.saveFragranceType(newFragranceType);
        FragranceTypeDto newFragranceTypeDto = fragranceTypeMapper.toFragranceTypeDto(newFragranceType);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(UriComponentsBuilder
                .newInstance()
                .path("/api/fragrancetypes/{id}")
                .buildAndExpand(newFragranceType.getId())
                .toUri());
        return new ResponseEntity<>(newFragranceTypeDto, headers, HttpStatus.CREATED);
    }

    // GET
    @Override   //  SINGLE  api/fragrancetypes/{fragranceTypeId}
    public ResponseEntity<FragranceTypeDto> getFragranceType(Integer fragranceTypeId) {
        FragranceType fragranceType = this.meliorService.findFragranceById(fragranceTypeId);
        if (fragranceType != null)
            return ResponseEntity.ok(fragranceTypeMapper.toFragranceTypeDto(fragranceType));
        return ResponseEntity.notFound().build();
    }

    @Override   //  ALL  api/fragrancetypes
    public ResponseEntity<List<FragranceTypeDto>> listFragranceType() {
        Collection<FragranceType> fragrances = this.meliorService.findAllFragrance();
        if (!fragrances.isEmpty())
            return ResponseEntity.ok(this.fragranceTypeMapper.toFragranceTypeDtoList(fragrances));
        return ResponseEntity.notFound().build();

    }

    //  PUT
    @Override   //  api/fragrancetypes/{fragranceTypeId}
    public ResponseEntity<FragranceTypeDto> updateFragranceType(Integer fragranceTypeId, FragranceTypeFieldsDto fragranceTypeFieldsDto) {
        FragranceType updateFragranceType = this.meliorService.findFragranceById(fragranceTypeId);
        if (updateFragranceType == null)
            return ResponseEntity.notFound().build();

        updateFragranceType.setName(fragranceTypeFieldsDto.getName());

        this.meliorService.saveFragranceType(updateFragranceType);
        return new ResponseEntity<>(fragranceTypeMapper.toFragranceTypeDto(updateFragranceType), HttpStatus.NO_CONTENT);
    }

    //  DELETE
    @Override   //  api/fragrancetypes/{fragranceTypeId}
    public ResponseEntity<Void> deleteFragranceType(Integer fragranceTypeId) {
        FragranceType fragrance = this.meliorService.findFragranceById(fragranceTypeId);
        if (fragrance == null)
            return ResponseEntity.notFound().build();
        this.meliorService.deleteFragranceType(fragrance);
        return ResponseEntity.noContent().build();
    }
}
