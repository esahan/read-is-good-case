package com.ege.readingisgood.web.controller;

import com.ege.readingisgood.service.customer.CustomerService;
import com.ege.readingisgood.web.model.CustomerDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = CustomerController.class)
class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CustomerService customerService;



    @Test
    void create_whenValidInput_thenReturns200() throws Exception {
        Mockito.when(customerService.createCustomer(any())).thenReturn(1L);
        CustomerDTO customer = CustomerDTO.builder().email("ege@ege.com").gender("M").name("ege").surname("sahan").build();
        mockMvc.perform(post("/api/v1/customers/customer")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(customer)))
                .andExpect(status().isCreated());
    }

    @Test
    void create_whenInvalidInput_thenReturns400() throws Exception {
        CustomerDTO customer = CustomerDTO.builder().email("ege.com").gender("M").name("ege").surname("sahan").build();
        mockMvc.perform(post("/api/v1/customers/customer")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(customer)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void get_whenValidInput_Returns200() throws Exception {
        mockMvc.perform(get("/api/v1/customers/customer/{id}",1L)
                        .contentType("application/json"))
                .andExpect(status().isOk());
    }

    @Test
    void get_whenInvalidInput_Returns400() throws Exception {
        mockMvc.perform(get("/api/v1/customers/customer/{id}",0L)
                        .contentType("application/json"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getOrders_whenCalled_Returns200() throws Exception {
        mockMvc.perform(get("/api/v1/customers/orders/{id}",1L)
                        .contentType("application/json"))
                .andExpect(status().isOk());
    }
}