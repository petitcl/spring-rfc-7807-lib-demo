package com.example.demo.books;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CreateBookRequestVM {
    @NotNull
    @Min(1)
    Long id;
    @NotNull
    @NotEmpty
    String title;
    @NotNull
    @NotEmpty
    String isbn;
}
