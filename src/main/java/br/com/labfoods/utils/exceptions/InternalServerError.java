package br.com.labfoods.utils.exceptions;

import org.springframework.http.HttpStatus;

import br.com.labfoods.utils.exceptions.error.Error;

public class InternalServerError extends RuntimeException {
  private static final  String MESSAGE = "An unknown error has occurred.";

  private final transient Error error;
  
  public InternalServerError() {
    super(MESSAGE);
    this.error = new Error(HttpStatus.INTERNAL_SERVER_ERROR, MESSAGE);
  }

  public InternalServerError(String message, Exception exception) {
    super(MESSAGE, exception);
    this.error = new Error(HttpStatus.INTERNAL_SERVER_ERROR, message, exception);
  }

  public InternalServerError( String locationField) {
    super(MESSAGE);
    this.error = new Error(MESSAGE, locationField);
  }

  public InternalServerError(Exception exception, String locationField) {
    super(MESSAGE, exception);
    this.error = new Error(HttpStatus.INTERNAL_SERVER_ERROR, MESSAGE, exception, locationField);
  }

  public Error getError() {
    return error;
  }
}