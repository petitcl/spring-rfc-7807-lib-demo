package com.example.demo.config;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public abstract class GenericExceptionHandler {

    private final ProblemDetailFormatter formatter;

    public GenericExceptionHandler(ProblemDetailFormatter formatter) {
        this.formatter = formatter;
    }

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<Object> handleGenericException(Throwable throwable, WebRequest request) {
        final HttpStatus statusCode = HttpStatus.INTERNAL_SERVER_ERROR;
        final ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                statusCode,
                throwable.getMessage()
        );
        formatter.format(problemDetail, throwable, request);
        final HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<>(problemDetail, headers, problemDetail.getStatus());
    }

}
