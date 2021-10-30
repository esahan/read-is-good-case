package com.ege.readingisgood.service.customer;

import com.ege.readingisgood.web.model.CustomerDTO;
import com.ege.readingisgood.web.model.OrderPagedList;
import org.springframework.data.domain.Pageable;

public interface CustomerService {

    Long createCustomer(CustomerDTO customerDTO);

    CustomerDTO getCustomer(Long id);

    OrderPagedList getCustomerOrders(Long id, Pageable pageable);

}
