package com.example.librarymanagement.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "BOOK_TABLE")
@NoArgsConstructor
@AllArgsConstructor
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "book_seq_gen")
    @SequenceGenerator(name = "book_seq_gen", sequenceName = "book_sequence", allocationSize = 1)
    private Long id;

    @Column(name = "book_title", length = 150, nullable = false)
    private String title;

    @Column(length = 100, nullable = false)
    private String author;

    // Use @JsonFormat for serialization to JSON with a specific date pattern
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    // Use @DateTimeFormat for deserialization from a specific date pattern in requests
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @Column(name = "pub_date", nullable = false)
    private LocalDate publicationDate;

    @Column(unique = true, nullable = false)
    private String isbn;
}
