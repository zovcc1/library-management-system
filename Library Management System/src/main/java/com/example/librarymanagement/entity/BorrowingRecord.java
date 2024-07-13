package com.example.librarymanagement.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "BORROWING_RECORD_TABLE")
public class BorrowingRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "borrowing_record_seq_gen")
    @SequenceGenerator(name = "borrowing_record_seq_gen", sequenceName = "borrowing_record_sequence", allocationSize = 1)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patron_id", nullable = false)
    private Patron patron;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    @Column(name = "borrow_date", nullable = false)
    private Date borrowDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    @Column(name = "return_date")
    private Date returnDate;

    @Column(name = "status", length = 20)
    private String status;
}
