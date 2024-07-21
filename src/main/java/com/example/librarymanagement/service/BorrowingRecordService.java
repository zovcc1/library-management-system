package com.example.librarymanagement.service;

import com.example.librarymanagement.dto.BorrowingRecordDto;
import com.example.librarymanagement.entity.Book;
import com.example.librarymanagement.entity.BorrowingRecord;
import com.example.librarymanagement.entity.Patron;
import com.example.librarymanagement.enums.BorrowingStatus;
import com.example.librarymanagement.exception.BookNotFoundException;
import com.example.librarymanagement.exception.NoPatronFoundException;
import com.example.librarymanagement.repository.BookRepository;
import com.example.librarymanagement.repository.BorrowingRecordRepository;
import com.example.librarymanagement.repository.PatronRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class BorrowingRecordService {

    private final BorrowingRecordRepository borrowingRecordRepository;
    private final PatronRepository patronRepository;
    private final BookRepository bookRepository;
    private final ModelMapper modelMapper;

    public BorrowingRecordService(BorrowingRecordRepository borrowingRecordRepository, PatronRepository patronRepository, BookRepository bookRepository, ModelMapper modelMapper) {
        this.borrowingRecordRepository = borrowingRecordRepository;
        this.patronRepository = patronRepository;
        this.bookRepository = bookRepository;
        this.modelMapper = modelMapper;
    }

    public BorrowingRecordDto borrowBook(Long bookId, Long patronId) {
        Patron patron = patronRepository.findById(patronId)
                .orElseThrow(() -> new NoPatronFoundException("Patron not found with id: " + patronId));
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new BookNotFoundException("Book not found with id: " + bookId));

        BorrowingRecord borrowingRecord = new BorrowingRecord();
        borrowingRecord.setPatron(patron);
        borrowingRecord.setBook(book);
        borrowingRecord.setBorrowDate(LocalDate.now());
        borrowingRecord.setStatus(BorrowingStatus.BORROWED);

        BorrowingRecord savedRecord = borrowingRecordRepository.save(borrowingRecord);
        return modelMapper.map(savedRecord , BorrowingRecordDto.class);
    }

    public BorrowingRecordDto returnBook(Long bookId, Long patronId) {
        BorrowingRecord borrowingRecord = borrowingRecordRepository
                .findByBookIdAndPatronIdAndStatus(bookId, patronId, BorrowingStatus.BORROWED)
                .orElseThrow(() -> new BookNotFoundException("Borrowing record not found for book id: " + bookId + " and patron id: " + patronId));

        borrowingRecord.setReturnDate(LocalDate.now());
        borrowingRecord.setStatus(BorrowingStatus.RETURNED);

        BorrowingRecord savedRecord = borrowingRecordRepository.save(borrowingRecord);
        return modelMapper.map(savedRecord,BorrowingRecordDto.class);
    }
}
