package com.ege.readingisgood.service.customer;

import com.ege.readingisgood.domain.Customer;
import com.ege.readingisgood.exceptions.BusinessException;
import com.ege.readingisgood.repositories.customer.CustomerRepository;
import com.ege.readingisgood.service.SequenceGeneratorServiceImpl;
import com.ege.readingisgood.service.order.OrderService;
import com.ege.readingisgood.web.mapper.CustomerMapper;
import com.ege.readingisgood.web.model.CustomerDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class CustomerServiceImplTest {

    @Mock
    MessageSource messageSource;

    @Mock
    CustomerRepository customerRepository;

    @Mock
    SequenceGeneratorServiceImpl sequenceGenerator;

    @Spy
    CustomerMapper customerMapper = Mappers.getMapper(CustomerMapper.class);

    @Mock
    OrderService orderService;

    @InjectMocks
    CustomerServiceImpl customerService;


    @Test
    void create_thenReturnsIdentifier(){
        CustomerDTO customer = CustomerDTO.builder().email("ege@ege.com").gender("M").name("ege").surname("sahan").build();
        Mockito.when(customerRepository.save(any())).thenReturn(Customer.builder().email("ege@ege.com").gender("M").name("ege").surname("sahan").id(1L).build());
        assertEquals(1L, customerService.createCustomer(customer));
    }

    @Test
    void get_whenValidId_thenReturnCustomer (){
        Customer customer = Customer.builder().email("ege@ege.com").gender("M").name("ege").surname("sahan").id(1L).build();
        Mockito.when(customerRepository.findById(any())).thenReturn(Optional.of(customer));
        CustomerDTO customerById = customerService.getCustomer(1L);
        assertEquals(customerById.getId(),customer.getId());
    }

    @Test
    void get_whenInValidId_thenThrow (){
        assertThrows(BusinessException.class,
                ()-> customerService.getCustomer(0L));
    }

    @Test
    void getOrder_whenInValidId_thenThrow (){
        assertThrows(BusinessException.class,
                ()-> customerService.getCustomerOrders(0L, null));
    }



}