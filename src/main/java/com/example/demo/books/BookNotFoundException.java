package com.example.demo.books;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.ErrorResponseException;

public class BookNotFoundException extends ErrorResponseException {
    public BookNotFoundException(Long bookId) {
        super(
                HttpStatus.NOT_FOUND,
                problemDetail(bookId),
                null
        );
    }

    private static ProblemDetail problemDetail(Long bookId) {
        final ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                HttpStatus.NOT_FOUND,
                "Book with id " + bookId + " not found"
        );
        problemDetail.setTitle("Book not found");
        return problemDetail;
    }
}
