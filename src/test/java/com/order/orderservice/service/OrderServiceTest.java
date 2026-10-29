package com.order.orderservice.service;

import com.order.orderservice.dto.OrderRequestDto;
import com.order.orderservice.kafka.event.OrderCreatedEvent;
import com.order.orderservice.kafka.producer.OrderProducer;
import com.order.orderservice.mapper.OrderMapper;
import com.order.orderservice.service.impl.OrderServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.UUID;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private OrderProducer orderProducer;

    @Mock
    private OrderMapper orderMapper;

    @InjectMocks
    private OrderServiceImpl orderService;

    @Test
    void createOrder_shouldMapRequestAndSendEvent() {
        OrderRequestDto orderRequest  = new OrderRequestDto(1L, "ProductName", 2);
        OrderCreatedEvent orderCreatedEvent  = new OrderCreatedEvent(UUID.randomUUID(), orderRequest.customerId(), orderRequest.productName(), orderRequest .quantity(), Instant.now());

        when(orderMapper.toOrderCreatedEvent(orderRequest )).thenReturn(orderCreatedEvent);
        orderService.createOrder(orderRequest );

        verify(orderMapper).toOrderCreatedEvent(orderRequest );
        verify(orderProducer).send(orderCreatedEvent );
    }
}