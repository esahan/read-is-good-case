package com.ege.readingisgood.service.order;

import com.ege.readingisgood.domain.Order;
import com.ege.readingisgood.exceptions.BusinessException;
import com.ege.readingisgood.repositories.order.OrderRepository;
import com.ege.readingisgood.service.SequenceGeneratorServiceImpl;
import com.ege.readingisgood.service.book.BookService;
import com.ege.readingisgood.web.mapper.OrderMapper;
import com.ege.readingisgood.web.model.OrderDTO;
import com.ege.readingisgood.web.model.OrderPagedList;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {

    @Mock
    MessageSource messageSource;

    @Mock
    SequenceGeneratorServiceImpl sequenceGenerator;

    @Mock
    OrderRepository orderRepository;

    @Spy
    OrderMapper orderMapper = Mappers.getMapper(OrderMapper.class);

    @Mock
    BookService bookService;

    @InjectMocks
    OrderServiceImpl orderService;


    @Test
    void create_thenReturnsIdentifier(){
        Map<Long,Integer> bookIdOrderCountMap = new HashMap<>();
        bookIdOrderCountMap.put(1L,1);
        OrderDTO order = OrderDTO.builder().bookOrderIdQuantityMap(bookIdOrderCountMap)
                .customerId(1L).purchaseDate(LocalDateTime.now()).build();
        Mockito.when(orderRepository.save(any())).thenReturn(Order.builder().customerId(1L).purchaseDate(LocalDateTime.now()).id(1L).build());
        assertEquals(1L, orderService.createOrder(order));
    }

    @Test
    void get_whenValidId_thenReturnOrder (){
        Order order = Order.builder().customerId(1L).purchaseDate(LocalDateTime.now()).id(1L).build();
        Mockito.when(orderRepository.findById(any())).thenReturn(Optional.of(order));
        OrderDTO orderById = orderService.getOrder(1L);
        assertEquals(orderById.getId(),order.getId());
    }

    @Test
    void get_whenInValidId_thenThrow (){
        assertThrows(BusinessException.class,
                ()-> orderService.getOrder(0L));
    }

    @Test
    void getCustomerOrder_whenValidId_thenReturnOrder (){
        Order order = Order.builder().customerId(1L).purchaseDate(LocalDateTime.now()).id(1L).build();
        Mockito.when(orderRepository.getOrderByCustomerId(any())).thenReturn(Collections.singletonList(order));
        List<OrderDTO> customerOrders = orderService.getCustomerOrders(1L);
        assertFalse(customerOrders.isEmpty());
    }

    @Test
    void getCustomerOrderPageable_returnOrder (){
        Order order = Order.builder().customerId(1L).purchaseDate(LocalDateTime.now()).id(1L).build();
        PageImpl<Order> page = new PageImpl<>(Collections.singletonList(order), PageRequest.of(0,20), 1);
        Mockito.when(orderRepository.getOrderByCustomerId(any(),any())).thenReturn(page);
        OrderPagedList customerOrders = orderService.getCustomerOrders(1L, PageRequest.of(0, 20));
        assertEquals(1L,customerOrders.getTotalElements());
    }

    @Test
    void getOrderPageable_dateBetween_returnOrder (){
        Order order = Order.builder().customerId(1L).purchaseDate(LocalDateTime.now()).id(1L).build();PageImpl<Order> page = new PageImpl<>(Collections.singletonList(order), PageRequest.of(0,20), 1);
        Mockito.when(orderRepository.getOrderByPurchaseDateBetween(any(),any(),any())).thenReturn(page);
        OrderPagedList ordersBetweenStartDateAndEndDate = orderService.getOrdersBetweenStartDateAndEndDate(new Date(), new Date(), PageRequest.of(0, 20));
        assertEquals(1L,ordersBetweenStartDateAndEndDate.getTotalElements());
    }


}