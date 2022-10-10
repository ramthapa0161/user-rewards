package com.customer.rewards.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ExceptionHandlerAdvice extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException e, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ErrorDto errorDto = new ErrorDto();
        e.getBindingResult().getAllErrors().stream().limit(1).forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errorDto.setErrorCode("400");
            errorDto.setMessage(errorMessage);
        });
        return new ResponseEntity<>(errorDto, HttpStatus.BAD_REQUEST);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(NoTransactionFoundException.class)
    public ResponseEntity<ErrorDto> handleNoTransactionExceptionHandler(NoTransactionFoundException e){
        ErrorDto error = new ErrorDto();
        error.setMessage(e.getMessage());
        error.setErrorCode("400");
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }


}
