package com.ege.readingisgood.service.customer;

import com.ege.readingisgood.domain.Customer;
import com.ege.readingisgood.exceptions.BusinessException;
import com.ege.readingisgood.repositories.customer.CustomerRepository;
import com.ege.readingisgood.service.SequenceGeneratorServiceImpl;
import com.ege.readingisgood.service.order.OrderService;
import com.ege.readingisgood.web.mapper.CustomerMapper;
import com.ege.readingisgood.web.model.CustomerDTO;
import com.ege.readingisgood.web.model.OrderPagedList;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Locale;
import java.util.Optional;

import static com.ege.readingisgood.exceptions.BusinessException.ServiceException.CUSTOMER_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService{

    private final MessageSource messageSource;
    private final CustomerRepository customerRepository;
    private final SequenceGeneratorServiceImpl sequenceGenerator;
    private final CustomerMapper customerMapper;
    private final OrderService orderService;


    @Override
    public Long createCustomer(CustomerDTO customerDTO) {
        Customer customer = customerMapper.toModel(customerDTO);
        customer.setId(sequenceGenerator.generateSequence(Customer.SEQUENCE_NAME));
        return customerRepository.save(customer).getId();
    }

    @Override
    public CustomerDTO getCustomer(Long id) {
        Optional<Customer> customerOptional = customerRepository.findById(id);
        return customerMapper.toDTO(customerOptional.orElseThrow(() ->
                new BusinessException(messageSource.getMessage(CUSTOMER_NOT_FOUND.getKey(), new Object[] {id}, Locale.getDefault())
                        ,CUSTOMER_NOT_FOUND.getStatus())));
    }

    public OrderPagedList getCustomerOrders(Long id, Pageable pageable){
        Optional<Customer> customerOptional = customerRepository.findById(id);
        if(customerOptional.isEmpty()){
            throw new BusinessException(messageSource.getMessage(CUSTOMER_NOT_FOUND.getKey(), new Object[] {id}, Locale.getDefault())
                    ,CUSTOMER_NOT_FOUND.getStatus());
        }
        return orderService.getCustomerOrders(id,pageable);
    }

}
