package com.kulushev.Orders.exception.exceptionHandler;

import com.kulushev.Orders.exception.ErrorResponseDto;
import com.kulushev.Orders.exception.notFound.OrderNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(value = {IllegalArgumentException.class,
            HttpMessageNotReadableException.class})
    public ResponseEntity<ErrorResponseDto> illegalArgumentException(Exception ex) {
        String errorMessage = String.format("%s", ex.getMessage());
        return ResponseEntity.status(400).body(new ErrorResponseDto(errorMessage));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(FieldError::getDefaultMessage)
                .toList();

        Map<String, Object> body = new HashMap<>();
        body.put("errors", errors);

        return ResponseEntity.status(400).body(body);
    }


    @ExceptionHandler(value = {OrderNotFoundException.class})
    public ResponseEntity<ErrorResponseDto> notFoundException(Exception ex) {
        String errorMessage = String.format("%s", ex.getMessage());
        return ResponseEntity.status(404).body(new ErrorResponseDto(errorMessage));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> unknownException(Exception ex) {
        log.error("Unexpected error", ex);
        String errorMessage = String.format("%s", ex.getMessage());
        return ResponseEntity.status(500).body(new ErrorResponseDto(errorMessage));
    }

}
