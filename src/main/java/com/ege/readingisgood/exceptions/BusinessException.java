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

        USER_NOT_FOUND("service.exception.user.not.found",HttpStatus.BAD_REQUEST),
        BOOK_NOT_FOUND("service.exception.book.not.found", HttpStatus.BAD_REQUEST),
        ORDER_NOT_FOUND("service.exception.order.not.found",HttpStatus.BAD_REQUEST),
        BOOK_QUANTITY_ON_HAND_BELOW_ZERO("service.exception.book.quantity.on.hand.below.zero",HttpStatus.BAD_REQUEST),
        ALL_BOOKS_NOT_EXIST("service.exception.book.all.books.not.exist", HttpStatus.BAD_REQUEST),
        REGISTER_USER_PRIVILEGE("service.exception.user.unauthorized.user",HttpStatus.UNAUTHORIZED);

        private String key;
        private HttpStatus status;


    }
}
