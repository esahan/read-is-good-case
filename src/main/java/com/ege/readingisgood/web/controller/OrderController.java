package com.ege.readingisgood.web.controller;

import com.ege.readingisgood.service.order.OrderService;
import com.ege.readingisgood.web.model.OrderDTO;
import com.ege.readingisgood.web.model.OrderPagedList;
import com.ege.readingisgood.web.model.ResponseModel;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.net.URI;
import java.util.Date;
import java.util.Locale;

import static com.ege.readingisgood.web.model.ResponseModel.ResponseMessages.*;

@RestController
@RequestMapping("/api/v1/orders")
@Validated
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final Environment env;
    private final MessageSource messageSource;

    @PostMapping("/order")
    public ResponseEntity<ResponseModel<Object>> createOrder(@Valid @RequestBody OrderDTO order) {
        Long orderId = orderService.createOrder(order);
        HttpHeaders httpHeaders = new HttpHeaders();
        String url = env.getProperty("server.host") + "/api/v1/orders/order/" + orderId;
        httpHeaders.setLocation(URI.create(url));
        return ResponseModel.buildResponseModel(null,HttpStatus.CREATED,httpHeaders,CREATED_SUCCESSFULLY.getMessage());
    }

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

    @GetMapping("/order/{id}")
    public ResponseEntity<ResponseModel<OrderDTO>> getOrder(@Positive @PathVariable("id") Long id) {
        OrderDTO order = orderService.getOrder(id);
        return ResponseModel.buildResponseModel(order,HttpStatus.OK,SUCCESSFUL_OPERATION.getMessage());
    }
}
