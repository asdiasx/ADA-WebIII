package com.example.sorteiosapi.system;

import com.example.sorteiosapi.exception.DataJogoException;
import com.example.sorteiosapi.exception.NumeroSorteioException;
import com.example.sorteiosapi.exception.SorteioException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.reactive.result.method.annotation.ResponseEntityExceptionHandler;

import java.util.NoSuchElementException;

@ControllerAdvice
public class ExceptionHandlerControllerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(SorteioException.class)
    public ResponseEntity<Object> handleSorteioException(SorteioException ex) {
        String errorMessage = ex.getMessage();
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(errorMessage, httpStatus);
    }

    @ExceptionHandler(DataJogoException.class)
    public ResponseEntity<Object> handleDataJogoException(DataJogoException ex) {
        String errorMessage = ex.getMessage();
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(errorMessage, httpStatus);
    }

    @ExceptionHandler(NumeroSorteioException.class)
    public ResponseEntity<Object> handleNumeroSorteioException(NumeroSorteioException ex) {
        String errorMessage = ex.getMessage();
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(errorMessage, httpStatus);
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<Object> handleNoSuchElementException(NoSuchElementException ex) {
        String errorMessage = ex.getMessage();
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(errorMessage, httpStatus);
    }
}
