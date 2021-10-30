package com.ege.readingisgood.web.controller;

import com.ege.readingisgood.service.customer.CustomerService;
import com.ege.readingisgood.web.model.CustomerDTO;
import com.ege.readingisgood.web.model.OrderPagedList;
import com.ege.readingisgood.web.model.ResponseModel;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.net.URI;

import static com.ege.readingisgood.web.model.ResponseModel.ResponseMessages.*;

@RestController
@RequestMapping("api/v1/customers")
@Validated
@RequiredArgsConstructor
public class CustomerController {

    private final Environment env;
    private final CustomerService customerService;

    @PostMapping("/customer")
    public ResponseEntity<ResponseModel<Object>> createCustomer(@Valid @RequestBody CustomerDTO customer) {
        Long customerId = customerService.createCustomer(customer);
        HttpHeaders httpHeaders = new HttpHeaders();
        String url = env.getProperty("server.host") + "/api/v1/customers/customer/" + customerId;
        httpHeaders.setLocation(URI.create(url));
        return ResponseModel.buildResponseModel(null,HttpStatus.CREATED,httpHeaders, CREATED_SUCCESSFULLY.getMessage());
    }

    @GetMapping("/customer/{id}")
    public ResponseEntity<ResponseModel<CustomerDTO>> getCustomerById(@Positive @PathVariable("id") Long id) {
        CustomerDTO customer = customerService.getCustomer(id);
        return ResponseModel.buildResponseModel(customer,HttpStatus.OK,SUCCESSFUL_OPERATION.getMessage());
    }

    @GetMapping("/orders/{id}")
    public ResponseEntity<ResponseModel<OrderPagedList>> getCustomerOrders(
            @Positive @PathVariable("id") Long id,
            @PageableDefault(size = 20) Pageable pageable) {
        OrderPagedList customerOrders = customerService.getCustomerOrders(id, pageable);
        return ResponseModel.buildResponseModel(customerOrders,HttpStatus.OK,SUCCESSFUL_OPERATION.getMessage());
    }
}
