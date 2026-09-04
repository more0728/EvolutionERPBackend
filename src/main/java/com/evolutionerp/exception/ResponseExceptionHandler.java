
package com.evolutionerp.exception;

import org.springframework.http.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import java.time.LocalDateTime;

@RestControllerAdvice
public class ResponseExceptionHandler {
  @ExceptionHandler(ModelNotFoundException.class)
  public ResponseEntity<CustomErrorRecord> handleNotFound(ModelNotFoundException ex, WebRequest req) {
    return new ResponseEntity<>(new CustomErrorRecord(LocalDateTime.now(), ex.getMessage(), req.getDescription(false)),
        HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<CustomErrorRecord> handleValid(MethodArgumentNotValidException ex, WebRequest req) {
    String msg = ex.getBindingResult().getFieldErrors().stream().map(f -> f.getField() + ": " + f.getDefaultMessage())
        .reduce((a, b) -> a + ", " + b).orElse("Validation error");
    return new ResponseEntity<>(new CustomErrorRecord(LocalDateTime.now(), msg, req.getDescription(false)),
        HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<CustomErrorRecord> handleAll(Exception ex, WebRequest req) {
    return new ResponseEntity<>(new CustomErrorRecord(LocalDateTime.now(), ex.getMessage(), req.getDescription(false)),
        HttpStatus.INTERNAL_SERVER_ERROR);
  }
}