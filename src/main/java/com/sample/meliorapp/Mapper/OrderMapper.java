package com.sample.meliorapp.Mapper;

import com.sample.meliorapp.model.Customer;
import com.sample.meliorapp.model.FragranceType;
import com.sample.meliorapp.model.Order;
import com.sample.meliorapp.rest.dto.CustomerDto;
import com.sample.meliorapp.rest.dto.FragranceTypeDto;
import com.sample.meliorapp.rest.dto.OrderDto;
import com.sample.meliorapp.rest.dto.OrderFieldsDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.Collection;
import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface OrderMapper {
    @Mapping(source = "customer.id", target = "customerId")
    OrderDto toOrderDto(Order order);
    Order toOrder(OrderDto orderDto);
    Order toOrder(OrderFieldsDto orderFieldsDto);
    List<OrderDto> toOrderDtoList(Collection<Order> orders);
    Collection<OrderDto> toOrderDtos(Collection<Order> orders);
    Collection<Order> toOrders(Collection<OrderDto> orderDtos);

    FragranceTypeDto toFragranceTypeDto(FragranceType fragranceType);
    FragranceType toFragranceType(FragranceTypeDto fragranceTypeDto);

}
