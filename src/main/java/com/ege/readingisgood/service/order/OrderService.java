package com.ege.readingisgood.service.order;

import com.ege.readingisgood.web.model.OrderDTO;
import com.ege.readingisgood.web.model.OrderPagedList;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;

public interface OrderService {
    OrderDTO getOrder(Long id);

    Long createOrder(OrderDTO orderDTO);

    OrderPagedList getCustomerOrders(Long customerId, Pageable pageable);

    List<OrderDTO> getCustomerOrders(Long customerId);

    OrderPagedList getOrdersBetweenStartDateAndEndDate(Date startDate, Date endDate, Pageable pageable);
}
