package com.ege.readingisgood.service.statistics;

import com.ege.readingisgood.service.order.OrderService;
import com.ege.readingisgood.web.model.OrderDTO;
import com.ege.readingisgood.web.model.UserStatisticsSummary;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Month;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.groupingBy;

@Service
@RequiredArgsConstructor
public class StatisticsServiceImpl implements StatisticsService {
    private final OrderService orderService;

    @Override
    public Map<Integer, Map<Month, UserStatisticsSummary>> getUserStatistic(Long customerId) {
        List<OrderDTO> customerOrders = orderService.getCustomerOrders(customerId);
        return customerOrders.stream().collect(groupingBy(x -> x.getPurchaseDate().getYear(), groupingBy(x -> x.getPurchaseDate().getMonth(),
                collectingAndThen(Collectors.toList(),
                        list -> {
                            BigDecimal totalPurchase = list.stream().map(OrderDTO::getTotalPurchaseAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
                            Integer totalBookCount = list.stream().map(x -> x.getBookOrderIdQuantityMap().values().stream().reduce(0, Integer::sum)).reduce(0, Integer::sum);
                            Integer totalOrderCount = list.size();
                            return new UserStatisticsSummary(totalOrderCount, totalBookCount, totalPurchase);
                        }
                ))));
    }
}
