package com.order.orderservice.service.impl;

import com.order.orderservice.dto.OrderRequestDto;
import com.order.orderservice.kafka.event.OrderCreatedEvent;
import com.order.orderservice.kafka.producer.OrderProducer;
import com.order.orderservice.mapper.OrderMapper;
import com.order.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderServiceImpl  implements OrderService {

    private final OrderProducer orderProducer;
    private final OrderMapper orderMapper;

    @Override
    public void createOrder(OrderRequestDto request) {
        // order is trimmed in mapper, mb it's good idea of using a mutable DTO or creating a copy.
        OrderCreatedEvent orderCreatedEvent = orderMapper.toOrderCreatedEvent(request); //kafka "dto"
        log.info("Creating order: orderId={}, customerId={}", orderCreatedEvent.orderId(), orderCreatedEvent.customerId());
        orderProducer.send(orderCreatedEvent);
    }
}
