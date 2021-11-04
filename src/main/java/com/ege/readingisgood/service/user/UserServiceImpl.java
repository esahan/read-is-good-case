package com.ege.readingisgood.service.user;

import com.ege.readingisgood.domain.User;
import com.ege.readingisgood.domain.UserRoles;
import com.ege.readingisgood.exceptions.BusinessException;
import com.ege.readingisgood.repositories.user.UserRepository;
import com.ege.readingisgood.service.SequenceGeneratorServiceImpl;
import com.ege.readingisgood.service.order.OrderService;
import com.ege.readingisgood.web.mapper.UserMapper;
import com.ege.readingisgood.web.model.OrderPagedList;
import com.ege.readingisgood.web.model.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.ege.readingisgood.exceptions.BusinessException.ServiceException.REGISTER_USER_PRIVILEGE;
import static com.ege.readingisgood.exceptions.BusinessException.ServiceException.USER_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final MessageSource messageSource;
    private final UserRepository userRepository;
    private final SequenceGeneratorServiceImpl sequenceGenerator;
    private final UserMapper userMapper;
    private final OrderService orderService;
    private final PasswordEncoder encoder;


    @Override
    public Long createUser(UserDTO userDTO) {
        User user = userMapper.toModel(userDTO);
        user.setId(sequenceGenerator.generateSequence(User.SEQUENCE_NAME));
        return userRepository.save(user).getId();
    }

    @Override
    public Long createUserAuth(UserDTO userDTO) {
        if (userRepository.existsByEmail(userDTO.getEmail())) {
            throw new IllegalArgumentException("Error: email already registered!");
        }
        validateRegisterProcess(userDTO);
        userDTO.setPassword(encoder.encode(userDTO.getPassword()));
        User user = userMapper.toModel(userDTO);
        user.setId(sequenceGenerator.generateSequence(User.SEQUENCE_NAME));
        user.setRoles(UserRoles.getByType(user.getType()).get().getRoles()); //NOSONAR already validated
        return userRepository.save(user).getId();
    }

    private void validateRegisterProcess(UserDTO user){
        List<String> userAuthorities = SecurityContextHolder.getContext().getAuthentication()
                .getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
        if(!UserRoles.CUSTOMER.getType().equals(user.getType()) && !userAuthorities.contains(UserRoles.Roles.ADMIN_ROLE)){
            throw new BusinessException(messageSource.getMessage(REGISTER_USER_PRIVILEGE.getKey(), null, Locale.getDefault())
                    ,REGISTER_USER_PRIVILEGE.getStatus());
        }
    }

    @Override
    public UserDTO getUser(Long id) {
        Optional<User> customerOptional = userRepository.findById(id);
        return userMapper.toDTO(customerOptional.orElseThrow(() ->
                new BusinessException(messageSource.getMessage(USER_NOT_FOUND.getKey(), new Object[] {id}, Locale.getDefault())
                        , USER_NOT_FOUND.getStatus())));
    }

    public OrderPagedList getUserOrders(Long id, Pageable pageable){
        Optional<User> customerOptional = userRepository.findById(id);
        if(customerOptional.isEmpty()){
            throw new BusinessException(messageSource.getMessage(USER_NOT_FOUND.getKey(), new Object[] {id}, Locale.getDefault())
                    , USER_NOT_FOUND.getStatus());
        }
        return orderService.getCustomerOrders(id,pageable);
    }

}
