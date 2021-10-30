package com.ege.readingisgood.service.book;

import com.ege.readingisgood.domain.Book;
import com.ege.readingisgood.exceptions.BusinessException;
import com.ege.readingisgood.repositories.book.BookRepository;
import com.ege.readingisgood.service.SequenceGeneratorServiceImpl;
import com.ege.readingisgood.web.mapper.BookMapper;
import com.ege.readingisgood.web.model.BookDTO;
import com.ege.readingisgood.web.model.BookPagedList;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;
import static com.ege.readingisgood.exceptions.BusinessException.ServiceException.*;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService{

    private final MessageSource messageSource;
    private final BookRepository bookRepository;
    private final SequenceGeneratorServiceImpl sequenceGenerator;
    private final BookMapper bookMapper;

    @Override
    public Long createBook(BookDTO bookDTO) {
        Book book = bookMapper.toModel(bookDTO);
        book.setId(sequenceGenerator.generateSequence(Book.SEQUENCE_NAME));
        return bookRepository.save(book).getId();
    }


    @Override
    public BookPagedList listAllBooks(Pageable pageable) {
        Page<Book> bookPage = bookRepository.findAll(pageable);
        List<BookDTO> bookContent = bookPage.stream().map(bookMapper::toDTO).collect(Collectors.toList());

        return new BookPagedList( bookContent, PageRequest.of(
                bookPage.getPageable().getPageNumber(),
                bookPage.getPageable().getPageSize()),
                bookPage.getTotalElements());

    }

    @Override
    public List<BookDTO> listBooksByIdList(List<Long> ids){
        List<Book> bookByIdIn = bookRepository.findBookByIdIn(ids);
        if(new HashSet<>(ids).size() != bookByIdIn.size()){
            throw new BusinessException(messageSource.getMessage(ALL_BOOKS_NOT_EXIST.getKey(), new Object[] {ids}, Locale.getDefault()),ALL_BOOKS_NOT_EXIST.getStatus());
        }
        return bookMapper.toDTOList(bookByIdIn);
    }

    @Override
    @Transactional
    public void updateStock(Long id, Integer quantity) {
        Optional<Book> bookById = bookRepository.findById(id);
        Book book = bookById.orElseThrow(() ->
                new BusinessException(messageSource.getMessage(BOOK_NOT_FOUND.getKey(), new Object[] {id}, Locale.getDefault())
                        ,BOOK_NOT_FOUND.getStatus()));
        if(quantity<0 && book.getQuantityOnHand()+quantity < 0){
            throw new BusinessException(messageSource.getMessage(BOOK_QUANTITY_ON_HAND_BELOW_ZERO.getKey(),
                    new Object[] {id}, Locale.getDefault()),BOOK_QUANTITY_ON_HAND_BELOW_ZERO.getStatus());
        }
        book.setQuantityOnHand(book.getQuantityOnHand()+quantity);
        bookRepository.save(book);
    }


}
