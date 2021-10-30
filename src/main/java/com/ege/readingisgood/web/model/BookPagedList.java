package com.ege.readingisgood.web.model;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

public class BookPagedList extends PageImpl<BookDTO> {
    public BookPagedList(List<BookDTO> content, Pageable pageable, long total) {
        super(content, pageable, total);
    }
}
