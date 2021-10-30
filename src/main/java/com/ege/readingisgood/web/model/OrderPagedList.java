package com.ege.readingisgood.web.model;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

public class OrderPagedList extends PageImpl<OrderDTO> {
    public OrderPagedList(List<OrderDTO> content, Pageable pageable, long total) {
        super(content, pageable, total);
    }
}
