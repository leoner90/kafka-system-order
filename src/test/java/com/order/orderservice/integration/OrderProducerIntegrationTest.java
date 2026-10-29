package com.order.orderservice.integration;

import com.order.orderservice.kafka.event.OrderCreatedEvent;
import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.kafka.support.serializer.JacksonJsonSerializer;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.time.Instant;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringJUnitConfig
@EmbeddedKafka(topics = "order-created", partitions = 1)
class OrderProducerIntegrationTest {

    @Autowired
    private EmbeddedKafkaBroker broker;

    //Not sure is there a lot of value in this test at all
    @Test
    void shouldSendMessageToKafka() throws Exception {
        //Kafka pre Setup
        //producerFactory - just fake embedded Kafka producer, KafkaTestUtils.producerProps(broker)
        DefaultKafkaProducerFactory<String, OrderCreatedEvent>  producerFactory = new DefaultKafkaProducerFactory<>(
                KafkaTestUtils.producerProps(broker),
                new StringSerializer(),
                new JacksonJsonSerializer<OrderCreatedEvent>()
        );

        try {
            // Kafka Setup
            KafkaTemplate<String, OrderCreatedEvent> kafkaTemplate = new KafkaTemplate<>(producerFactory);

            // kafka dto - msg
            OrderCreatedEvent orderCreatedEvent = new OrderCreatedEvent(UUID.randomUUID(), 1L, "ProductName", 2, Instant.now());

            //send msg
            SendResult<String, OrderCreatedEvent> result = kafkaTemplate.send("order-created",
                    orderCreatedEvent.orderId().toString(),
                    orderCreatedEvent
            ).get(10, TimeUnit.SECONDS);

            //assert
            assertEquals("order-created", result.getRecordMetadata().topic());
            assertTrue(result.getRecordMetadata().offset() >= 0);
        } finally {
            //destroy
            producerFactory.destroy();
        }
    }
}
