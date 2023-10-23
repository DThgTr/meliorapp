package com.sample.meliorapp.Mapper;

import com.sample.meliorapp.model.Customer;
import com.sample.meliorapp.rest.dto.CustomerDto;
import com.sample.meliorapp.rest.dto.CustomerFieldsDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.Collection;
import java.util.List;
@Mapper(uses = OrderMapper.class, componentModel = MappingConstants.ComponentModel.SPRING)
public interface CustomerMapper {
    CustomerDto toCustomerDto(Customer customer);
    Customer toCustomer(CustomerDto customerDto);
    Customer toCustomer(CustomerFieldsDto customerFieldsDto);
    List<CustomerDto> toCustomerDtoList(Collection<Customer> customerCollection);
    Collection<Customer> toCustomers(Collection<CustomerDto> customerDtos);
}
