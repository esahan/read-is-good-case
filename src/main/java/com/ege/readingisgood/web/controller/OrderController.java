package com.ege.readingisgood.web.controller;

import com.ege.readingisgood.security.UserDetailsImpl;
import com.ege.readingisgood.service.order.OrderService;
import com.ege.readingisgood.web.model.OrderDTO;
import com.ege.readingisgood.web.model.OrderPagedList;
import com.ege.readingisgood.web.model.ResponseModel;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.Date;
import java.util.Locale;

import static com.ege.readingisgood.web.model.ResponseModel.ResponseMessages.ORDER_CREATED;
import static com.ege.readingisgood.web.model.ResponseModel.ResponseMessages.SUCCESSFUL_OPERATION;

@RestController
@RequestMapping("/api/v1/orders")
@Validated
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final MessageSource messageSource;

    @PreAuthorize("hasRole('CUSTOMER')")
    @PostMapping("/order")
    public ResponseEntity<ResponseModel<Object>> createOrder(@Valid @RequestBody OrderDTO order, UriComponentsBuilder ucBuilder) {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        order.setCustomerId(userDetails.getId());
        Long orderId = orderService.createOrder(order);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(ucBuilder.path("/api/v1/orders/order/{id}").buildAndExpand(orderId).toUri());
        return ResponseModel.buildResponseModel(null,HttpStatus.CREATED,httpHeaders,ORDER_CREATED.getMessage());
    }

    @PreAuthorize("hasRole('EMPLOYEE')")
    @GetMapping("/list-orders")
    public ResponseEntity<ResponseModel<OrderPagedList>> getOrdersBetweenStartDateAndEndDate(
            @RequestParam @DateTimeFormat(pattern = "dd-MM-yyyy") Date startDate,
            @RequestParam @DateTimeFormat(pattern = "dd-MM-yyyy") Date endDate,
            @PageableDefault(size = 20) Pageable pageable) {
        OrderPagedList orders = orderService.getOrdersBetweenStartDateAndEndDate(startDate, endDate, pageable);
        if(0 < startDate.compareTo(endDate))
            throw new IllegalArgumentException(messageSource.getMessage("validation.order.endDate.earlierThan.startDate",null, Locale.getDefault()));
        return ResponseModel.buildResponseModel(orders,HttpStatus.OK,SUCCESSFUL_OPERATION.getMessage());
    }

    @PreAuthorize("hasRole('EMPLOYEE') or hasRole('CUSTOMER')")
    @GetMapping("/order/{id}")
    public ResponseEntity<ResponseModel<OrderDTO>> getOrder(@Positive @PathVariable("id") Long id) {
        OrderDTO order = orderService.getOrder(id);
        return ResponseModel.buildResponseModel(order,HttpStatus.OK,SUCCESSFUL_OPERATION.getMessage());
    }
}
