
package com.evolutionerp.exception;

import org.springframework.dao.DataIntegrityViolationException;
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

  // Skill §A6: conflicto de integridad (duplicado/FK) → 409, sin matchear strings SQL.
  @ExceptionHandler({ DataIntegrityViolationException.class, ConflictException.class })
  public ResponseEntity<CustomErrorRecord> handleConflict(RuntimeException ex, WebRequest req) {
    String msg = ex instanceof ConflictException ? ex.getMessage() : "Registro duplicado o conflicto de integridad";
    return new ResponseEntity<>(
        new CustomErrorRecord(LocalDateTime.now(), msg, req.getDescription(false)),
        HttpStatus.CONFLICT);
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