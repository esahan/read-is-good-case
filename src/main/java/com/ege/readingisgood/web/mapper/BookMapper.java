package com.ege.readingisgood.web.mapper;

import com.ege.readingisgood.domain.Book;
import com.ege.readingisgood.web.model.BookDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class BookMapper {

    public abstract BookDTO toDTO(Book book);

    public abstract Book toModel(BookDTO order);

    public abstract List<BookDTO> toDTOList(List<Book> books);
}
