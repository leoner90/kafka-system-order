package com.order.orderservice.producer;

import com.order.orderservice.kafka.event.OrderCreatedEvent;
import com.order.orderservice.kafka.producer.OrderProducer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;

import java.time.Instant;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.same;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class OrderProducerTest {

    @Mock
    private KafkaTemplate<String, OrderCreatedEvent> kafkaTemplate;

    @InjectMocks
    private OrderProducer orderProducer;

    @Test
    void shouldSendOrderCreatedEventToKafka() {
        UUID orderId = UUID.randomUUID();
        OrderCreatedEvent orderCreatedEvent = new OrderCreatedEvent(orderId, 1L, "ProductName", 2, Instant.now());
        CompletableFuture<SendResult<String, OrderCreatedEvent>> completableFuture = new CompletableFuture<>();

        when(kafkaTemplate.send("order-created", orderId.toString(), orderCreatedEvent)).thenReturn(completableFuture);
        orderProducer.send(orderCreatedEvent);

        //additionally simulate Kafka publishing failure ( maybe need separate test)
        completableFuture.completeExceptionally(new RuntimeException("Kafka unavailable"));

        verify(kafkaTemplate).send(eq("order-created"), eq(orderId.toString()), same(orderCreatedEvent)); // was sent called?
        verifyNoMoreInteractions(kafkaTemplate); // no other calls were done
    }
}