package com.example.librarymanagement.entity;

import com.example.librarymanagement.enums.BorrowingStatus;
import com.example.librarymanagement.exception.InvalidReturnDateException;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

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
    private LocalDate borrowDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    @Column(name = "return_date")
    private LocalDate returnDate;

    @Column(name = "status", length = 20)
    @Enumerated(EnumType.STRING)
    private BorrowingStatus status;

    @PrePersist
    @PreUpdate
    public void validateReturnDate() {
        if (returnDate != null && returnDate.isBefore(borrowDate)) {
            throw new InvalidReturnDateException("Return date cannot be before borrow date");
        }
    }


}
