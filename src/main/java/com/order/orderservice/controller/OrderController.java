package com.order.orderservice.controller;

import com.order.orderservice.controller.swagger_interface.OrderSwaggerInterface;
import com.order.orderservice.dto.OrderRequestDto;
import com.order.orderservice.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class OrderController implements OrderSwaggerInterface {

    private final OrderService orderService;

    @Override
    @PostMapping("/orders")
    public ResponseEntity<Void> createOrder(@Valid @RequestBody OrderRequestDto request) {
        log.info("Received order request: customerId={}, productName={}, quantity={}", request.customerId(), request.productName(), request.quantity());
        orderService.createOrder(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
