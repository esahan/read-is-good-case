package com.ege.readingisgood.web.mapper;

import com.ege.readingisgood.domain.Customer;
import com.ege.readingisgood.web.model.CustomerDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class CustomerMapper {

    public abstract CustomerDTO toDTO(Customer customer);

    public abstract Customer toModel(CustomerDTO customer);
}
