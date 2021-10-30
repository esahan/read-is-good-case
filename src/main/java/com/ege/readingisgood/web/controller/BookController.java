package com.ege.readingisgood.web.controller;

import com.ege.readingisgood.service.book.BookService;
import com.ege.readingisgood.web.model.BookDTO;
import com.ege.readingisgood.web.model.BookPagedList;
import com.ege.readingisgood.web.model.ResponseModel;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.net.URI;

import static com.ege.readingisgood.web.model.ResponseModel.ResponseMessages.*;

@RestController
@RequestMapping("/api/v1/books")
@Validated
@RequiredArgsConstructor
public class BookController {

    private final Environment env;
    private final BookService bookService;

    @PostMapping("/book")
    public ResponseEntity<ResponseModel<Object>> createBook(@Valid @RequestBody BookDTO book) {
        Long bookId = bookService.createBook(book);
        HttpHeaders httpHeaders = new HttpHeaders();
        String url = env.getProperty("server.host") + "/api/v1/books/book/" + bookId;
        httpHeaders.setLocation(URI.create(url));
        return ResponseModel.buildResponseModel(null,HttpStatus.CREATED,httpHeaders,CREATED_SUCCESSFULLY.getMessage());
    }

    @PatchMapping(path ="/book/{id}")
    public ResponseEntity<ResponseModel<Object>> addBookStock(@PathVariable("id") Long id, @Positive @RequestParam Integer quantity) {
        bookService.updateStock(id,quantity);
        return ResponseModel.buildResponseModel(null,HttpStatus.OK,UPDATED_SUCCESSFULLY.getMessage());
    }

    @GetMapping("/book/list")
    public ResponseEntity<ResponseModel<BookPagedList>> getBookList(@PageableDefault(size = 20)Pageable pageable){
        BookPagedList allBooksPage = bookService.listAllBooks(pageable);
        return ResponseModel.buildResponseModel(allBooksPage, HttpStatus.OK,SUCCESSFUL_OPERATION.getMessage());
    }
}
