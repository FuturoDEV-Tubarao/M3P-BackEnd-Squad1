package br.com.labfoods.utils.exceptions;

import org.springframework.http.HttpStatus;

import br.com.labfoods.utils.exceptions.error.Error;

public class ConflictException extends RuntimeException {
  private static final  String MESSAGE = "Already in use.";

  private final transient Error error;
  
  public ConflictException() {
    super(MESSAGE);
    this.error = new Error(HttpStatus.BAD_REQUEST, MESSAGE);
  }

  public ConflictException(String message, Exception exception) {
    super(MESSAGE, exception);
    this.error = new Error(HttpStatus.BAD_REQUEST, message, exception);
  }

  public ConflictException(String locationField) {
    super(MESSAGE);
    this.error = new Error(MESSAGE, locationField);
  }

  public ConflictException(Exception exception, String locationField) {
    super(MESSAGE, exception);
    this.error = new Error(HttpStatus.BAD_REQUEST, MESSAGE, exception, locationField);
  }

  public ConflictException(Exception exception) {
    super(exception.getMessage(), exception);
    this.error = new Error(HttpStatus.BAD_REQUEST, exception.getMessage(), exception);
  }

  public Error getError() {
    return error;
  }
}