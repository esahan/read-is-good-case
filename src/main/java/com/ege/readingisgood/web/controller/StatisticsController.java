package com.ege.readingisgood.web.controller;

import com.ege.readingisgood.service.statistics.StatisticsService;
import com.ege.readingisgood.web.model.ResponseModel;
import com.ege.readingisgood.web.model.UserStatisticsSummary;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Month;
import java.util.Map;

import static com.ege.readingisgood.web.model.ResponseModel.ResponseMessages.*;

@RestController
@RequestMapping("/api/v1/statistics")
@Validated
@RequiredArgsConstructor
public class StatisticsController {

    private final StatisticsService statisticsService;

    @GetMapping("/overview/{id}")
    public ResponseEntity<ResponseModel<Map<Integer, Map<Month, UserStatisticsSummary>>>> getStatistic(@PathVariable("id") Long customerId) {
        return ResponseModel.buildResponseModel(statisticsService.getUserStatistic(customerId),HttpStatus.OK,SUCCESSFUL_OPERATION.getMessage());
    }

}
