package com.ege.readingisgood.web.controller;

import com.ege.readingisgood.security.UserDetailsImpl;
import com.ege.readingisgood.service.statistics.StatisticsService;
import com.ege.readingisgood.web.model.ResponseModel;
import com.ege.readingisgood.web.model.UserStatisticsSummary;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Month;
import java.util.Map;

import static com.ege.readingisgood.web.model.ResponseModel.ResponseMessages.SUCCESSFUL_OPERATION;

@RestController
@RequestMapping("/api/v1/statistics")
@Validated
@RequiredArgsConstructor
public class StatisticsController {

    private final StatisticsService statisticsService;

    @PreAuthorize("hasRole('EMPLOYEE')")
    @GetMapping("/overview/{id}")
    public ResponseEntity<ResponseModel<Map<Integer, Map<Month, UserStatisticsSummary>>>> getUserStatistic(@PathVariable(value = "id")  Long customerId) {
        return ResponseModel.buildResponseModel(statisticsService.getUserStatistic(customerId),HttpStatus.OK ,SUCCESSFUL_OPERATION.getMessage());
    }


    @PreAuthorize("hasRole('CUSTOMER')")
    @GetMapping("/overview")
    public ResponseEntity<ResponseModel<Map<Integer, Map<Month, UserStatisticsSummary>>>> getSelfStatistic() {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseModel.buildResponseModel(statisticsService.getUserStatistic(userDetails.getId()),HttpStatus.OK ,SUCCESSFUL_OPERATION.getMessage());
    }

}
