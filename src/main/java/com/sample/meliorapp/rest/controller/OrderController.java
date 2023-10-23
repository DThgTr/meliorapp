package com.sample.meliorapp.rest.controller;

import com.sample.meliorapp.Mapper.FragranceTypeMapper;
import com.sample.meliorapp.Mapper.OrderMapper;
import com.sample.meliorapp.model.Order;
import com.sample.meliorapp.rest.api.OrdersApi;
import com.sample.meliorapp.rest.dto.OrderDto;
import com.sample.meliorapp.rest.dto.OrderFieldsDto;
import com.sample.meliorapp.rest.service.MeliorService;
import org.aspectj.weaver.ast.Or;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.List;

@RestController
@CrossOrigin(exposedHeaders = "errors, content-type")
@RequestMapping("/api")
public class OrderController implements OrdersApi {
    private final MeliorService meliorService;
    private final OrderMapper orderMapper;
    private final FragranceTypeMapper fragranceTypeMapper;

    public OrderController(MeliorService meliorService,
                           OrderMapper orderMapper,
                           FragranceTypeMapper fragranceTypeMapper) {
        this.meliorService = meliorService;
        this.orderMapper = orderMapper;
        this.fragranceTypeMapper = fragranceTypeMapper;
    }
    @Override
    public ResponseEntity<OrderDto> getOrder(Integer orderId) {
        Order order = this.meliorService.findOrderById(orderId);
        if (order != null)
            return ResponseEntity.ok(orderMapper.toOrderDto(order));
        return ResponseEntity.notFound().build();
    }

    @Override
    public ResponseEntity<List<OrderDto>> listOrders() {
        Collection<Order> orders = this.meliorService.findAllOrder();
        if (!orders.isEmpty())
            return ResponseEntity.ok(orderMapper.toOrderDtoList(orders));
        return ResponseEntity.notFound().build();
    }

    @Override
    public ResponseEntity<OrderDto> updateOrder(Integer orderId, OrderFieldsDto orderFieldsDto) {
        Order updateOrder = this.meliorService.findOrderById(orderId);
        if (updateOrder == null)
            return ResponseEntity.notFound().build();

        updateOrder.setQuantity(orderFieldsDto.getQuantity());
        updateOrder.setFragranceType(this.fragranceTypeMapper.toFragranceType(orderFieldsDto.getFragranceType()));

        this.meliorService.saveOrder(updateOrder);
        return new ResponseEntity<>(orderMapper.toOrderDto(updateOrder), HttpStatus.NO_CONTENT);
    }

    @Override
    public ResponseEntity<Void> deleteOrder(Integer orderId) {
        Order order = this.meliorService.findOrderById(orderId);
        if (order == null)
            return ResponseEntity.notFound().build();

        this.meliorService.deleteOrder(order);
        return ResponseEntity.noContent().build();
    }
}
