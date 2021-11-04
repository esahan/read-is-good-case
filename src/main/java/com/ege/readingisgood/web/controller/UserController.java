package com.ege.readingisgood.web.controller;

import com.ege.readingisgood.security.UserDetailsImpl;
import com.ege.readingisgood.service.user.UserService;
import com.ege.readingisgood.web.model.OrderPagedList;
import com.ege.readingisgood.web.model.ResponseModel;
import com.ege.readingisgood.web.model.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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

import static com.ege.readingisgood.web.model.ResponseModel.ResponseMessages.CREATED_SUCCESSFULLY;
import static com.ege.readingisgood.web.model.ResponseModel.ResponseMessages.SUCCESSFUL_OPERATION;

@RestController
@RequestMapping("/api/v1/users")
@Validated
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/user")
    public ResponseEntity<ResponseModel<Object>> createUser(@Valid @RequestBody UserDTO customer, UriComponentsBuilder ucBuilder) {
        Long customerId = userService.createUser(customer);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(ucBuilder.path("api/v1/customers/customer/{id}").buildAndExpand(customerId).toUri());
        return ResponseModel.buildResponseModel(null,HttpStatus.CREATED,httpHeaders, CREATED_SUCCESSFULLY.getMessage());
    }

    @PreAuthorize("hasRole('EMPLOYEE')")
    @GetMapping("/user/{id}")
    public ResponseEntity<ResponseModel<UserDTO>> getUserById(@Positive @PathVariable("id") Long id) {
        UserDTO customer = userService.getUser(id);
        return ResponseModel.buildResponseModel(customer,HttpStatus.OK,SUCCESSFUL_OPERATION.getMessage());
    }

    @PreAuthorize("hasRole('EMPLOYEE')")
    @GetMapping("/orders/{id}")
    public ResponseEntity<ResponseModel<OrderPagedList>> getCustomerOrders(
            @Positive @PathVariable("id") Long id,
            @PageableDefault(size = 20) Pageable pageable) {
        OrderPagedList customerOrders = userService.getUserOrders(id, pageable);
        return ResponseModel.buildResponseModel(customerOrders,HttpStatus.OK,SUCCESSFUL_OPERATION.getMessage());
    }

    @PreAuthorize("hasRole('CUSTOMER')")
    @GetMapping("/orders")
    public ResponseEntity<ResponseModel<OrderPagedList>> getSelfOrders(@PageableDefault(size = 20) Pageable pageable) {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        OrderPagedList customerOrders = userService.getUserOrders(userDetails.getId(), pageable);
        return ResponseModel.buildResponseModel(customerOrders,HttpStatus.OK,SUCCESSFUL_OPERATION.getMessage());
    }
}
