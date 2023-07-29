package com.example.demo.books;

import lombok.Value;

@Value
public class BookResponse {
    long id;
    String title;
    String isbn;
}
