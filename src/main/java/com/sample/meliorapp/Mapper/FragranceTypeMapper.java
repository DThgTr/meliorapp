package com.sample.meliorapp.Mapper;

import com.sample.meliorapp.model.FragranceType;
import com.sample.meliorapp.rest.dto.FragranceTypeDto;
import com.sample.meliorapp.rest.dto.FragranceTypeFieldsDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.Collection;
import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface FragranceTypeMapper {
    FragranceTypeDto toFragranceTypeDto(FragranceType fragranceType);

    FragranceType toFragranceType(FragranceTypeDto fragranceTypeDto);

    FragranceType toFragranceType(FragranceTypeFieldsDto fragranceTypeFieldsDto);

    List<FragranceTypeDto> toFragranceTypeDtoList(Collection<FragranceType> fragranceTypes);
    Collection<FragranceType> toFragranceTypes (Collection<FragranceTypeDto> fragranceTypeDtos);

}
