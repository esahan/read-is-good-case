package com.ege.readingisgood.service.user;

import com.ege.readingisgood.domain.User;
import com.ege.readingisgood.exceptions.BusinessException;
import com.ege.readingisgood.repositories.user.UserRepository;
import com.ege.readingisgood.service.SequenceGeneratorServiceImpl;
import com.ege.readingisgood.service.order.OrderService;
import com.ege.readingisgood.web.mapper.UserMapper;
import com.ege.readingisgood.web.model.UserDTO;
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
class UserServiceImplTest {

    @Mock
    MessageSource messageSource;

    @Mock
    UserRepository userRepository;

    @Mock
    SequenceGeneratorServiceImpl sequenceGenerator;

    @Spy
    UserMapper userMapper = Mappers.getMapper(UserMapper.class);

    @Mock
    OrderService orderService;

    @InjectMocks
    UserServiceImpl userService;


    @Test
    void create_thenReturnsIdentifier(){
        UserDTO customer = UserDTO.builder().email("ege@ege.com").gender("M").name("ege").surname("sahan").build();
        Mockito.when(userRepository.save(any())).thenReturn(User.builder().email("ege@ege.com").gender("M").name("ege").surname("sahan").id(1L).build());
        assertEquals(1L, userService.createUser(customer));
    }

    @Test
    void get_whenValidId_thenReturnCustomer (){
        User user = User.builder().email("ege@ege.com").gender("M").name("ege").surname("sahan").id(1L).build();
        Mockito.when(userRepository.findById(any())).thenReturn(Optional.of(user));
        UserDTO customerById = userService.getUser(1L);
        assertEquals(customerById.getId(), user.getId());
    }

    @Test
    void get_whenInValidId_thenThrow (){
        assertThrows(BusinessException.class,
                ()-> userService.getUser(0L));
    }

    @Test
    void getOrder_whenInValidId_thenThrow (){
        assertThrows(BusinessException.class,
                ()-> userService.getUserOrders(0L, null));
    }



}