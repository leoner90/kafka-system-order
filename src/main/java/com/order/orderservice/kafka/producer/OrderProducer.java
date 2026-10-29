package com.order.orderservice.kafka.producer;

import com.order.orderservice.kafka.event.OrderCreatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class OrderProducer {

    private final KafkaTemplate<String, OrderCreatedEvent> kafkaTemplate;

    public void send(OrderCreatedEvent orderCreatedEvent) {
        kafkaTemplate.send("order-created", orderCreatedEvent.orderId().toString(), orderCreatedEvent)
                .whenComplete((result, exception) -> {
                    if (exception != null) {
                        log.error("Failed to publish order: orderId={}", orderCreatedEvent.orderId(), exception);
                    } else {
                        log.info("Order published to Kafka: orderId={}, topic={}", orderCreatedEvent.orderId(), result.getRecordMetadata().topic());
                    }
                });
    }
}