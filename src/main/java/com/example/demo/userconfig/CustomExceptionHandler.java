package com.example.demo.userconfig;

import com.example.demo.books.BookPublishingException;
import com.example.demo.config.ProblemDetailFormatter;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.net.URI;

@Order(0)
@ControllerAdvice
public class CustomExceptionHandler {

    private final ProblemDetailFormatter formatter;

    public CustomExceptionHandler(ProblemDetailFormatter formatter) {
        this.formatter = formatter;
    }

    @ExceptionHandler(BookPublishingException.class)
    public ResponseEntity<Object> handleGenericException(BookPublishingException ex, WebRequest request) {
        final HttpStatus statusCode = HttpStatus.I_AM_A_TEAPOT;
        final ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                statusCode,
                ex.getMessage()
        );
        problemDetail.setType(URI.create("https://example.com/i-am-a-teapot"));
        formatter.format(problemDetail, ex, request);
        final HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<>(problemDetail, headers, problemDetail.getStatus());
    }
}
