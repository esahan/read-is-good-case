package com.ege.readingisgood.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class BusinessException extends RuntimeException{

    private final HttpStatus status;

    public BusinessException(String message,HttpStatus status) {
        super(message);
        this.status = status;
    }

    public BusinessException(String message, Throwable cause,HttpStatus status) {
        super(message, cause);
        this.status = status;
    }


    @Getter
    @AllArgsConstructor
    public enum ServiceException {

        CUSTOMER_NOT_FOUND("service.exception.customer.not.found",HttpStatus.BAD_REQUEST),
        BOOK_NOT_FOUND("service.exception.book.not.found", HttpStatus.BAD_REQUEST),
        ORDER_NOT_FOUND("service.exception.order.not.found",HttpStatus.BAD_REQUEST),
        BOOK_QUANTITY_ON_HAND_BELOW_ZERO("service.exception.book.quantity.on.hand.below.zero",HttpStatus.BAD_REQUEST),
        ALL_BOOKS_NOT_EXIST("service.exception.book.all.books.not.exist", HttpStatus.BAD_REQUEST);

        private String key;
        private HttpStatus status;


    }
}
