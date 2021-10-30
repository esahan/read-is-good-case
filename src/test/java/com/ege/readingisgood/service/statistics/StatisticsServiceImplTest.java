package com.ege.readingisgood.service.statistics;

import com.ege.readingisgood.service.order.OrderService;
import com.ege.readingisgood.web.model.OrderDTO;
import com.ege.readingisgood.web.model.UserStatisticsSummary;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class StatisticsServiceImplTest {

    @Mock
    OrderService orderService;

    @InjectMocks
    StatisticsServiceImpl statisticsService;

    @Test
    void get_whenFoundData_thenReturnOverview(){
        Map<Long,Integer> bookIdOrderCountMap = new HashMap<>();
        bookIdOrderCountMap.put(5L,10);
        OrderDTO order = OrderDTO.builder().bookOrderIdQuantityMap(bookIdOrderCountMap)
                .customerId(1L).purchaseDate(LocalDateTime.now()).totalPurchaseAmount(BigDecimal.TEN).build();
        Mockito.when(orderService.getCustomerOrders(any())).thenReturn(Collections.singletonList(order));
        Map<Integer, Map<Month, UserStatisticsSummary>> userStatistic = statisticsService.getUserStatistic(1L);
        assertEquals(1, userStatistic.size());
    }

}