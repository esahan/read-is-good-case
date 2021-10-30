package com.ege.readingisgood.service.book;

import com.ege.readingisgood.domain.Book;
import com.ege.readingisgood.exceptions.BusinessException;
import com.ege.readingisgood.repositories.book.BookRepository;
import com.ege.readingisgood.service.SequenceGeneratorServiceImpl;
import com.ege.readingisgood.web.mapper.BookMapper;
import com.ege.readingisgood.web.model.BookDTO;
import com.ege.readingisgood.web.model.BookPagedList;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;


@ExtendWith(MockitoExtension.class)
class BookServiceImplTest {

    @Mock
    MessageSource messageSource;

    @Mock
    SequenceGeneratorServiceImpl sequenceGenerator;

    @Mock
    BookRepository bookRepository;

    @Spy
    BookMapper bookMapper = Mappers.getMapper(BookMapper.class);

    @InjectMocks
    BookServiceImpl bookService;


    @Test
    void create_thenReturnsIdentifier(){
        BookDTO bookDTO = BookDTO.builder().author("ege").publisher("akasha").quantityOnHand(10).title("myBook").price(BigDecimal.TEN).build();
        Mockito.when(bookRepository.save(any())).thenReturn(Book.builder().id(1L).author("ege").publisher("akasha").quantityOnHand(10).title("myBook").price(BigDecimal.TEN).build());
        assertEquals(1L, bookService.createBook(bookDTO));
    }

    @Test
    void list_thenReturnsBooks(){
        Book book = Book.builder().author("ege").publisher("akasha").quantityOnHand(10).title("myBook").price(BigDecimal.TEN).build();
        PageImpl<Book> page = new PageImpl<>(Collections.singletonList(book), PageRequest.of(0,20), 1);
        Mockito.when(bookRepository.findAll(any(Pageable.class))).thenReturn(page);
        BookPagedList bookPagedList = bookService.listAllBooks(PageRequest.of(0, 20));
        assertEquals(1L,bookPagedList.getTotalElements());
    }

    @Test
    void listByIdList_ThenReturnBooks(){
        Book book = Book.builder().author("ege").publisher("akasha").quantityOnHand(10).title("myBook").price(BigDecimal.TEN).build();
        Mockito.when(bookRepository.findBookByIdIn(any())).thenReturn(Collections.singletonList(book));
        List<BookDTO> bookList = bookService.listBooksByIdList(List.of(1L));
        assertEquals(1,bookList.size());
    }

    @Test
    void listByIdList_whenInvalidOrMismatchId_ThenThrows(){
        Book book = Book.builder().author("ege").publisher("akasha").quantityOnHand(10).title("myBook").price(BigDecimal.TEN).build();
        Mockito.when(bookRepository.findBookByIdIn(any())).thenReturn(Collections.singletonList(book));
        List<Long> ids = Arrays.asList(1L, 2L);
        assertThrows(BusinessException.class,()-> bookService.listBooksByIdList(ids));
    }


    @Test
    void updateStock_whenInvalidStockChange_thenThrows(){
        Book book = Book.builder().author("ege").publisher("akasha").quantityOnHand(10).title("myBook").price(BigDecimal.TEN).build();
        Mockito.when(bookRepository.findById(any())).thenReturn(Optional.of(book));
        assertThrows(BusinessException.class,
                ()-> bookService.updateStock(1L,-15));
    }

    @Test
    void updateStock_whenInvalidBookId_thenThrows(){
        Mockito.when(bookRepository.findById(any())).thenReturn(Optional.empty());
        assertThrows(BusinessException.class, ()-> bookService.updateStock(1L,5));
    }


}