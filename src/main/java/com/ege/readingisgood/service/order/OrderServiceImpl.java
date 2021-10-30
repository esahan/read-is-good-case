package com.ege.readingisgood.service.order;

import com.ege.readingisgood.domain.Order;
import com.ege.readingisgood.exceptions.BusinessException;
import com.ege.readingisgood.repositories.order.OrderRepository;
import com.ege.readingisgood.service.SequenceGeneratorServiceImpl;
import com.ege.readingisgood.service.book.BookService;
import com.ege.readingisgood.web.mapper.OrderMapper;
import com.ege.readingisgood.web.model.BookDTO;
import com.ege.readingisgood.web.model.OrderDTO;
import com.ege.readingisgood.web.model.OrderPagedList;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static com.ege.readingisgood.exceptions.BusinessException.ServiceException.*;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService{

    private final OrderRepository orderRepository;
    private final SequenceGeneratorServiceImpl sequenceGenerator;
    private final MessageSource messageSource;
    private final OrderMapper orderMapper;
    private final BookService bookService;


    @Override
    public OrderDTO getOrder(Long id) {
        Optional<Order> orderOptional = orderRepository.findById(id);
        return orderMapper.toDTO(orderOptional.orElseThrow(()->
                new BusinessException(messageSource.getMessage(ORDER_NOT_FOUND.getKey(),
                        new Object[] {id}, Locale.getDefault()),ORDER_NOT_FOUND.getStatus())));
    }

    @Override
    @Transactional
    public Long createOrder(OrderDTO orderDTO) {
        Order order = orderMapper.toModel(orderDTO);
        List<Long> bookIds = new ArrayList<>(orderDTO.getBookOrderIdQuantityMap().keySet());
        List<BookDTO> bookList = bookService.listBooksByIdList(bookIds);
        order.setTotalPurchaseAmount(calculatePrice(bookList, order.getBookOrderIdQuantityMap()));
        reduceQuantityOnHand(order.getBookOrderIdQuantityMap());
        order.setPurchaseDate(LocalDateTime.now());
        order.setId(sequenceGenerator.generateSequence(Order.SEQUENCE_NAME));
        return orderRepository.save(order).getId();
    }

    private BigDecimal calculatePrice(List<BookDTO> bookList, Map<Long,Integer> bookOrder){
        return bookList.stream().map(x -> x.getPrice().multiply(
                new BigDecimal(bookOrder.get(x.getId())))).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private void reduceQuantityOnHand(Map<Long,Integer> bookIdOrderCountMap){
        bookIdOrderCountMap.forEach((key, value) -> bookService.updateStock(key, -value));
    }

    @Override
    public OrderPagedList getCustomerOrders(Long customerId, Pageable pageable){
        Page<Order> orderPage = orderRepository.getOrderByCustomerId(customerId, pageable);
        return convertPageToPageImpl(orderPage);
    }

    @Override
    public List<OrderDTO> getCustomerOrders(Long customerId){
        List<Order> orderByCustomerIdList = orderRepository.getOrderByCustomerId(customerId);
        return orderMapper.toDTO(orderByCustomerIdList);
    }

    @Override
    public OrderPagedList getOrdersBetweenStartDateAndEndDate(Date startDate, Date endDate, Pageable pageable) {
        Page<Order> orderPage = orderRepository.getOrderByPurchaseDateBetween(startDate, endDate, pageable);
        return convertPageToPageImpl(orderPage);
    }

    private OrderPagedList convertPageToPageImpl(Page<Order> orderPage){
        List<OrderDTO> orderContent = orderPage.stream().map(orderMapper::toDTO).collect(Collectors.toList());
        return new OrderPagedList(orderContent, PageRequest.of(orderPage.getPageable().getPageNumber(),
                orderPage.getPageable().getPageSize()),
                orderPage.getTotalElements());
    }
}
