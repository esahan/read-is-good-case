package com.ege.readingisgood.web.controller;

import com.ege.readingisgood.service.book.BookService;
import com.ege.readingisgood.web.model.BookDTO;
import com.ege.readingisgood.web.model.BookPagedList;
import com.ege.readingisgood.web.model.ResponseModel;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

import static com.ege.readingisgood.web.model.ResponseModel.ResponseMessages.*;

@RestController
@RequestMapping("/api/v1/books")
@Validated
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @PreAuthorize("hasRole('EMPLOYEE')")
    @PostMapping("/book")
    public ResponseEntity<ResponseModel<Object>> createBook(@Valid @RequestBody BookDTO book, UriComponentsBuilder ucBuilder) {
        Long bookId = bookService.createBook(book);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(ucBuilder.path("/api/v1/books/book/{id}").buildAndExpand(bookId).toUri());
        return ResponseModel.buildResponseModel(null,HttpStatus.CREATED,httpHeaders,CREATED_SUCCESSFULLY.getMessage());
    }

    @PreAuthorize("hasRole('EMPLOYEE')")
    @PatchMapping(path ="/book/{id}")
    public ResponseEntity<ResponseModel<Object>> addBookStock(@PathVariable("id") Long id, @Positive @RequestParam Integer quantity) {
        bookService.updateStock(id,quantity);
        return ResponseModel.buildResponseModel(null,HttpStatus.OK,UPDATED_SUCCESSFULLY.getMessage());
    }

    @PreAuthorize("hasRole('EMPLOYEE') or hasRole('CUSTOMER')")
    @GetMapping("/book/list")
    public ResponseEntity<ResponseModel<BookPagedList>> getBookList(@PageableDefault(size = 20)Pageable pageable){
        BookPagedList allBooksPage = bookService.listAllBooks(pageable);
        return ResponseModel.buildResponseModel(allBooksPage, HttpStatus.OK,SUCCESSFUL_OPERATION.getMessage());
    }
}
