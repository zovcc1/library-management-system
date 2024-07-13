package com.example.librarymanagement.entity;
import com.example.librarymanagement.entity.Book;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

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

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    @Column(name = "pub_date"  , nullable = false)
    private Date publicationDate;

    @Column(unique = true, nullable = false)
    private String isbn;
}
