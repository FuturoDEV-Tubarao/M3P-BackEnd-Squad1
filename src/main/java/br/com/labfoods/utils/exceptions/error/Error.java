package br.com.labfoods.utils.exceptions.error;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;

import lombok.Data;
import lombok.Builder;

@Data
@Builder
public class Error {

    private HttpStatus statusCode;
    private String message;
    private final List<ErrorDetail> errors = new ArrayList<>();

    public Error() {
        this.statusCode = HttpStatus.INTERNAL_SERVER_ERROR;
        this.message =  HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase();
    }

    public Error(HttpStatus statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }

    public Error(String message) {
        this.statusCode = HttpStatus.BAD_REQUEST;
        this.message = message;
    }

    public Error(String message, String locationField) {
        this.statusCode = HttpStatus.BAD_REQUEST;
        this.message = message;
        addDetail(message, locationField);
    }

    public Error(HttpStatus statusCode, String message, Exception exception) {
        this.statusCode = statusCode;
        this.message = message;
        addDetail(exception, null);
    }

    public Error(HttpStatus statusCode, String message, Exception exception, String locationField) {
        this.statusCode = statusCode;
        this.message = message;
        addDetail(exception, locationField);
    }

    public void addDetail(Exception exception, String locationField) {
        ErrorDetail detail = ErrorDetail.builder()
            .reason(exception.getLocalizedMessage())
            .locationField(locationField == null ? "" : locationField)
            .build();
        errors.add(detail);
    }
    

    public void addDetail(String message, String locationField) {
        ErrorDetail detail = ErrorDetail.builder()
            .reason(message)
            .locationField(locationField)
            .build();
        errors.add(detail);
    }
}
