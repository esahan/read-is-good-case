package com.ege.readingisgood.web.controller;

import com.ege.readingisgood.service.statistics.StatisticsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = StatisticsController.class)
class StatisticsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StatisticsService statisticsService;

    @Test
    void getOverview_whenValidInput_Returns200() throws Exception {
        mockMvc.perform(get("/api/v1/statistics/overview/{id}",1L)
                        .contentType("application/json"))
                .andExpect(status().isOk());
    }

}