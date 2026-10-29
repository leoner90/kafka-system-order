package com.order.orderservice.controller;

import com.order.orderservice.dto.OrderRequestDto;
import com.order.orderservice.service.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OrderController.class)
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private OrderService orderService;

    //order Creation success
    @Test
    void shouldCreateOrderSuccessfully() throws Exception {

        OrderRequestDto request = new OrderRequestDto(1L, "ProductName", 2);

        mockMvc.perform(post("/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());

        verify(orderService, times(1)).createOrder(request);
    }

    //if DTO validation errors
    @Test
    void shouldReturnBadRequestForInvalidOrder() throws Exception {
        OrderRequestDto request = new OrderRequestDto(null, "", 0);

        mockMvc.perform(post("/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());

        verifyNoInteractions(orderService);
    }

    //if negative customer id passed (a bit redundant test)
    @Test
    void shouldReturnBadRequestForNegativeCustomerId() throws Exception {
        OrderRequestDto request = new OrderRequestDto(-5L, "Laptop", 2);

        mockMvc.perform(post("/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$", hasItem("customerId: Customer ID must be positive")));

        verifyNoInteractions(orderService);
    }
}
