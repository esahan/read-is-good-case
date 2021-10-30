package com.ege.readingisgood.service.book;

import com.ege.readingisgood.web.model.BookDTO;
import com.ege.readingisgood.web.model.BookPagedList;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BookService {

    Long createBook(BookDTO bookDTO);

    BookPagedList listAllBooks(Pageable pageable);

    List<BookDTO> listBooksByIdList(List<Long> ids);

    void updateStock(Long id, Integer quantity);

}
