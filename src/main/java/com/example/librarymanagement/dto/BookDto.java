package com.example.librarymanagement.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookDto {
    private String title;
    private String author;

    // Use @JsonFormat for serialization to JSON with a specific date pattern
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    // Use @DateTimeFormat for deserialization from a specific date pattern in requests
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate publicationDate;

    private String isbn;
}
