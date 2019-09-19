package pl.com.sages.bookstore.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @AllArgsConstructor
    @Data
    static class NotFoundError {
        private String description;
    }

    @ExceptionHandler(EmptyResultDataAccessException.class)
    public ResponseEntity<NotFoundError> handleEntityNotFoundOnDelete(EmptyResultDataAccessException e) {
        return new ResponseEntity<>(new NotFoundError(e.getMessage()), HttpStatus.NOT_FOUND);
    }

}
