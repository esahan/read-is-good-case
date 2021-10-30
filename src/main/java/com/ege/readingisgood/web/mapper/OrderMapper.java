package com.ege.readingisgood.web.mapper;

import com.ege.readingisgood.domain.Order;
import com.ege.readingisgood.web.model.OrderDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;


@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class OrderMapper {

    public abstract OrderDTO toDTO(Order order);

    public abstract List<OrderDTO> toDTO(List<Order> order);

    public abstract Order toModel(OrderDTO order);
}
