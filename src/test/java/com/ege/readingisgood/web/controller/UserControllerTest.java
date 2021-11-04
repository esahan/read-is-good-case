package com.ege.readingisgood.web.controller;

import com.ege.readingisgood.security.AuthEntryPointJwt;
import com.ege.readingisgood.security.JwtUtils;
import com.ege.readingisgood.service.user.UserService;
import com.ege.readingisgood.web.model.UserDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = UserController.class)
//@SpringBootTest
//@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    @MockBean
    private JwtUtils jwtUtils;

    @MockBean
    private AuthEntryPointJwt authEntryPointJwt;

    @MockBean
    private com.ege.readingisgood.security.UserDetailsServiceImpl UserDetailsServiceImpl;

    @Test
    @WithMockUser(value = "testUser",roles = {"ADMIN"})
    void create_whenValidInput_thenReturns200() throws Exception {
        Mockito.when(userService.createUser(any())).thenReturn(1L);
        UserDTO customer = UserDTO.builder().email("ege@ege.com")
                .type("CUSTOMER").gender("M").name("ege").surname("sahan").build();
        mockMvc.perform(post("/api/v1/users/user")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(customer)))
                .andExpect(status().isCreated());
    }

    @Test
    @WithMockUser(value = "testUser",roles = {"ADMIN"})
    void create_whenInvalidInput_thenReturns400() throws Exception {
        UserDTO customer = UserDTO.builder().email("ege.com").gender("M").name("ege").surname("sahan").build();
        mockMvc.perform(post("/api/v1/users/user")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(customer)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(value = "testUser",roles = {"EMPLOYEE"})
    void get_whenValidInput_Returns200() throws Exception {
        mockMvc.perform(get("/api/v1/users/user/{id}",1L)
                        .contentType("application/json"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(value = "testUser",roles = {"EMPLOYEE"})
    void get_whenInvalidInput_Returns400() throws Exception {
        mockMvc.perform(get("/api/v1/users/user/{id}",0L)
                        .contentType("application/json"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(value = "testUser",roles = {"EMPLOYEE"})
    void getOrders_whenCalled_Returns200() throws Exception {
        mockMvc.perform(get("/api/v1/users/orders/{id}",1L)
                        .contentType("application/json"))
                .andExpect(status().isOk());
    }
}