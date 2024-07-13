package com.example.librarymanagement.service;

import com.example.librarymanagement.dto.BorrowingRecordDto;
import com.example.librarymanagement.entity.Book;
import com.example.librarymanagement.entity.BorrowingRecord;
import com.example.librarymanagement.entity.Patron;
import com.example.librarymanagement.exception.BookNotFoundException;
import com.example.librarymanagement.exception.NoPatronFoundException;
import com.example.librarymanagement.repository.BookRepository;
import com.example.librarymanagement.repository.BorrowingRecordRepository;
import com.example.librarymanagement.repository.PatronRepository;
import com.example.librarymanagement.mapper.BorrowingRecordMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class BorrowingRecordService {

    @Autowired
    private BorrowingRecordRepository borrowingRecordRepository;

    @Autowired
    private PatronRepository patronRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BorrowingRecordMapper borrowingRecordMapper;

    public BorrowingRecordDto borrowBook(Long bookId, Long patronId) {
        Patron patron = patronRepository.findById(patronId)
                .orElseThrow(() -> new NoPatronFoundException("Patron not found with id: " + patronId));
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new BookNotFoundException("Book not found with id: " + bookId));

        BorrowingRecord borrowingRecord = new BorrowingRecord();
        borrowingRecord.setPatron(patron);
        borrowingRecord.setBook(book);
        borrowingRecord.setBorrowDate(new Date());
        borrowingRecord.setStatus("BORROWED");

        BorrowingRecord savedRecord = borrowingRecordRepository.save(borrowingRecord);
        return borrowingRecordMapper.toDto(savedRecord);
    }

    public BorrowingRecordDto returnBook(Long bookId, Long patronId) {
        Optional<BorrowingRecord> borrowingRecordOpt = borrowingRecordRepository
                .findByBookIdAndPatronIdAndStatus(bookId, patronId, "BORROWED");

        if (borrowingRecordOpt.isEmpty()) {
            throw new BookNotFoundException("Borrowing record not found for book id: " + bookId + " and patron id: " + patronId);
        }

        BorrowingRecord borrowingRecord = borrowingRecordOpt.get();
        borrowingRecord.setReturnDate(new Date());
        borrowingRecord.setStatus("RETURNED");

        BorrowingRecord savedRecord = borrowingRecordRepository.save(borrowingRecord);
        return borrowingRecordMapper.toDto(savedRecord);
    }
}
