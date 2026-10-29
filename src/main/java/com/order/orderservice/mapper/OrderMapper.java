package com.order.orderservice.mapper;

import com.order.orderservice.dto.OrderRequestDto;
import com.order.orderservice.kafka.event.OrderCreatedEvent;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.Instant;
import java.util.UUID;

@Mapper(componentModel = "spring", imports = {UUID.class, Instant.class})
public interface OrderMapper
{
    @Mapping(target = "orderId", expression = "java(UUID.randomUUID())")
    @Mapping(target = "createdTime", expression = "java(Instant.now())")
    @Mapping(target = "productName", expression = "java(request.productName().trim())")
    OrderCreatedEvent toOrderCreatedEvent(OrderRequestDto request);
}
