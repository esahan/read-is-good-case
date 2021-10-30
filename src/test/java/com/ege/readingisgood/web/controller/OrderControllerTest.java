package com.ege.readingisgood.web.controller;

import com.ege.readingisgood.service.order.OrderService;
import com.ege.readingisgood.web.model.OrderDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = OrderController.class)
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private OrderService orderService;

    @Test
    void create_whenValidInput_thenReturns200() throws Exception {
        Mockito.when(orderService.createOrder(any())).thenReturn(1L);
        Map<Long,Integer> bookIdOrderCountMap = new HashMap<>();
        bookIdOrderCountMap.put(1L,1);
        OrderDTO orderDTO = OrderDTO.builder()
                .bookOrderIdQuantityMap(bookIdOrderCountMap)
                .customerId(1L)
                .purchaseDate(LocalDateTime.now()).build();
        mockMvc.perform(post("/api/v1/orders/order")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(orderDTO)))
                .andExpect(status().isCreated());
    }

    @Test
    void create_whenInvalidInput_thenReturns400() throws Exception {
        OrderDTO orderDTO = OrderDTO.builder()
                .customerId(1L)
                .purchaseDate(LocalDateTime.now()).build();
        mockMvc.perform(post("/api/v1/orders/order")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(orderDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getAllOrdersBetweenDates_whenValidInput_Returns200() throws Exception {
        mockMvc.perform(get("/api/v1/orders/list-orders")
                        .contentType("application/json")
                        .param("startDate", "01-01-2021")
                        .param("endDate", "01-12-2021"))
                .andExpect(status().isOk());
    }

    @Test
    void getAllOrdersBetweenDates_whenInvalidInput_Returns500() throws Exception {
        mockMvc.perform(get("/api/v1/orders/list-orders")
                        .contentType("application/json")
                        .param("startDate", "01-12-2021")
                        .param("endDate", "01-01-2021"))
                .andExpect(status().isInternalServerError());
    }

    @Test
    void get_whenValidInput_Returns200() throws Exception {
        mockMvc.perform(get("/api/v1/orders//order/{id}",1L)
                        .contentType("application/json"))
                .andExpect(status().isOk());
    }

    @Test
    void get_whenInValidInput_Returns400() throws Exception {
        mockMvc.perform(get("/api/v1/orders//order/{id}", 0L)
                        .contentType("application/json"))
                .andExpect(status().isBadRequest());
    }

}