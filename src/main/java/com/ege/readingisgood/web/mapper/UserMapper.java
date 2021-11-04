package com.ege.readingisgood.web.mapper;

import com.ege.readingisgood.domain.User;
import com.ege.readingisgood.web.model.UserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class UserMapper {

    public abstract UserDTO toDTO(User user);

    public abstract User toModel(UserDTO customer);
}
