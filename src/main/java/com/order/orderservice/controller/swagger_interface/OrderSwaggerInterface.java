package com.order.orderservice.controller.swagger_interface;

import com.order.orderservice.dto.OrderRequestDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ResponseEntity;

public interface OrderSwaggerInterface {
    @Operation(summary = "Create an order", description = "Creates an order and publishes an event to Kafka.")

    @ApiResponse(responseCode = "201", description = "Order created")
    @ApiResponse(responseCode = "400", description = "Invalid request")
    @ApiResponse(responseCode = "500", description = "Internal server error")

    ResponseEntity<Void> createOrder(OrderRequestDto request);
}