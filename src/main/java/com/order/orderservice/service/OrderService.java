package com.order.orderservice.service;

import com.order.orderservice.dto.OrderRequestDto;

public interface OrderService {
    void createOrder(OrderRequestDto request);
}