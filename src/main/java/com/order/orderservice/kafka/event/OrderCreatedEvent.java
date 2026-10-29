package com.order.orderservice.kafka.event;

import java.time.Instant;
import java.util.UUID;

public record OrderCreatedEvent(UUID orderId, Long customerId, String productName, Integer quantity, Instant createdTime) {}