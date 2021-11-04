package com.ege.readingisgood.service.user;

import com.ege.readingisgood.web.model.OrderPagedList;
import com.ege.readingisgood.web.model.UserDTO;
import org.springframework.data.domain.Pageable;

public interface UserService {

    Long createUser(UserDTO userDTO);

    Long createUserAuth(UserDTO userDTO);

    UserDTO getUser(Long id);

    OrderPagedList getUserOrders(Long id, Pageable pageable);

}
