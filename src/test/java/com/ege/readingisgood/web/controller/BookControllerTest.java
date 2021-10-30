package com.ege.readingisgood.web.controller;

import com.ege.readingisgood.service.book.BookService;
import com.ege.readingisgood.web.model.BookDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@WebMvcTest(controllers = BookController.class)
class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    BookService bookService;

    BookDTO validBookDTO;
    BookDTO invalidBookDTO;

    @BeforeAll
    void setup(){
        validBookDTO = BookDTO.builder()
                .author("ege")
                .publisher("akasha")
                .quantityOnHand(10)
                .title("myBook")
                .price(BigDecimal.TEN).build();

        invalidBookDTO = BookDTO.builder()
                .author("")
                .publisher("akasha")
                .quantityOnHand(10)
                .title("myBook")
                .price(BigDecimal.TEN).build();

        Mockito.when(bookService.createBook(any())).thenReturn(1L);
    }

    @Test
    void create_whenValidInput_thenReturns200() throws Exception {
        mockMvc.perform(post("/api/v1/books/book")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(validBookDTO)))
                .andExpect(status().isCreated());
    }

    @Test
    void create_whenInvalidInput_thenReturns400() throws Exception {
        mockMvc.perform(post("/api/v1/books/book")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(invalidBookDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void addStock_whenValidInput_thenReturns200() throws Exception {
        mockMvc.perform(patch("/api/v1/books/book/{id}", 1L)
                        .contentType("application/json")
                        .param("quantity", "1"))
                        .andExpect(status().isOk());
    }

    @Test
    void addStock_whenInvalidInput_thenReturns400() throws Exception {
        mockMvc.perform(patch("/api/v1/books/book/{id}", 1L)
                        .contentType("application/json")
                        .param("quantity", "0"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getBookList_whenCalled_Returns200() throws Exception {
        mockMvc.perform(get("/api/v1/books/book/list")
                        .contentType("application/json"))
                .andExpect(status().isOk());
    }

}