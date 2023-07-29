package com.example.demo.books;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.ErrorResponseException;

public class BookAlreadyExistsException extends ErrorResponseException {
    public BookAlreadyExistsException(Long bookId) {
        super(
                HttpStatus.CONFLICT,
                problemDetail(bookId),
                null
        );
    }

    private static ProblemDetail problemDetail(Long bookId) {
        final ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                HttpStatus.CONFLICT,
                "Book already exists:" + bookId
        );
        problemDetail.setTitle("Book already exists");
        return problemDetail;
    }
}
