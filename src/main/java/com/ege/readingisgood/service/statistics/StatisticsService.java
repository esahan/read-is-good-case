package com.ege.readingisgood.service.statistics;

import com.ege.readingisgood.web.model.UserStatisticsSummary;

import java.time.Month;
import java.util.Map;

public interface StatisticsService {
    Map<Integer, Map<Month, UserStatisticsSummary>> getUserStatistic(Long customerId);
}
