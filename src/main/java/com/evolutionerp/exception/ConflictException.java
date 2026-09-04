package com.evolutionerp.exception;

// Conflicto de integridad detectado en el servicio (duplicado pre-validado).
// Se traduce a 409 CONFLICT vía ResponseExceptionHandler.
public class ConflictException extends RuntimeException {
  public ConflictException(String message) {
    super(message);
  }
}
