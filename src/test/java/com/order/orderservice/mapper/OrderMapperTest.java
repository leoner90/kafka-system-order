package com.order.orderservice.mapper;

import com.order.orderservice.dto.OrderRequestDto;
import com.order.orderservice.kafka.event.OrderCreatedEvent;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;

class OrderMapperTest {

    private final OrderMapper orderMapper = Mappers.getMapper(OrderMapper.class);

    //check that trim in mapper works
    @Test
    void shouldTrimProductName() {
        OrderRequestDto request = new OrderRequestDto(1L, "  ProductName  ", 2);
        OrderCreatedEvent orderCreatedEvent = orderMapper.toOrderCreatedEvent(request);
        assertEquals("ProductName", orderCreatedEvent.productName());
    }
}