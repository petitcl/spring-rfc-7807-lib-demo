package com.example.demo.books;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/v1/books")
public class BooksController {

    private final HashMap<Long, BookResponse> books = new HashMap<>(
            Map.of(
                    1L, new BookResponse(1, "The Stand", "978-0307743688"),
                    2L, new BookResponse(2, "The Shining", "978-0307743657"),
                    3L, new BookResponse(3, "The Long Walk", "978-0451196712")
            )
    );

    @GetMapping("/{bookId}")
    public BookResponse getBook(@PathVariable Long bookId) {
        final var book = books.get(bookId);
        if (book == null) {
            throw new BookNotFoundException(bookId);
        }
        return book;
    }

    @PostMapping
    public BookResponse createBook(@RequestBody @Valid CreateBookRequestVM request) {
        final var newBook = new BookResponse(request.getId(), request.getTitle(), request.getIsbn());
        if (books.containsKey(newBook.getId())) {
            throw new BookAlreadyExistsException(newBook.getId());
        }
        books.put(newBook.getId(), newBook);
        return newBook;
    }

    @PostMapping("/{bookId}/read")
    public void readBook(@PathVariable Long bookId) {
        throw new RuntimeException("I don't know how to read!");
    }

    @PostMapping("/{bookId}/publish")
    public void publishBook(@PathVariable Long bookId) {
        throw new BookPublishingException(bookId);
    }

}
