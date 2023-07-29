package com.example.demo.books;

public class BookPublishingException extends RuntimeException{
    public BookPublishingException(Long bookId) {
        super("I don't know how to publish book " + bookId);
    }
}
