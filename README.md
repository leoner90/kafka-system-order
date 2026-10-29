\# Kafka Order System



A simple event-driven application consisting of \*\*3 independent microservices\*\* that communicate asynchronously using Apache Kafka.



\## Microservices



This project is part of a system split into three separate repositories:



\- \*\*\[Order Service](https://github.com/leoner90/kafka-system-order)\*\* — Entry point of the system. Handles order creation and status updates.

\- \*\*\[Inventory Service](https://github.com/leoner90/kafka-system-inventory)\*\* — Manages product stock and availability.

\- \*\*\[Notification Service](https://github.com/leoner90/kafka-system-notification)\*\* — Sends email notifications based on Kafka events.



\## Getting Started



To run and fully test the system:



1\. Clone all three repositories.

2\. Rename `.env.example` to `.env` and configure the database credentials for the Order and Inventory services.

3\. Start Apache Kafka on port `9092`.

4\. Start all three microservices.

5\. Use Postman to send a \*\*POST\*\* request with a JSON body to the \*\*Order Service\*\*, which serves as the system's entry point.



\## Technologies



\- \*\*Java 21\*\*

\- \*\*Spring Boot 4\*\*

\- \*\*Spring Data JPA / Hibernate\*\*

\- \*\*Apache Kafka\*\* — asynchronous communication between microservices

\- \*\*MySQL 8+\*\* — database

\- \*\*JavaMailSender\*\* — email notifications

\- \*\*Gradle\*\* — build automation

\- \*\*JUnit 5 / Mockito\*\* — testing



\## Environment Variables



Database credentials are configured using environment variables:



```properties

username=${DB\_USERNAME}

password=${DB\_PASSWORD}

```

