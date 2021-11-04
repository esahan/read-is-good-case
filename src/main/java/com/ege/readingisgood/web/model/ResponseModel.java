package com.ege.readingisgood.web.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseModel<T> {
    private HttpStatus httpStatus;
    private T content;
    private String message;

    @AllArgsConstructor
    @Getter
    public enum ResponseMessages{
        CREATED_SUCCESSFULLY("Data Created Successfully"),
        ORDER_CREATED("Order Created Successfully"),
        UPDATED_SUCCESSFULLY("Data Updated Successfully"),
        SUCCESSFUL_OPERATION("Operation Completed Successfully");
        private String message;
    }

    public static <T> ResponseEntity<ResponseModel<T>> buildResponseModel(T content, HttpStatus status, HttpHeaders headers, String message){
        ResponseModel<T> model = new ResponseModel<>();
        model.setContent(content);
        model.setHttpStatus(status);
        model.setMessage(message);
        return new ResponseEntity<>(model, headers, status);
    }

    public static <T> ResponseEntity<ResponseModel<T>> buildResponseModel(T content, HttpStatus status, String message){
        ResponseModel<T> model = new ResponseModel<>();
        model.setContent(content);
        model.setHttpStatus(status);
        model.setMessage(message);
        return new ResponseEntity<>(model, status);
    }



}
