package com.example.librarymanagement.controller;

import com.example.librarymanagement.dto.BorrowingRecordDto;
import com.example.librarymanagement.service.BorrowingRecordService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class BorrowingRecordController {


    private final BorrowingRecordService borrowingRecordService;

    public BorrowingRecordController(BorrowingRecordService borrowingRecordService) {
        this.borrowingRecordService = borrowingRecordService;
    }

    @PostMapping("/borrow/{bookId}/patron/{patronId}")
    public ResponseEntity<BorrowingRecordDto> borrowBook(@PathVariable Long bookId, @PathVariable Long patronId) {
        BorrowingRecordDto borrowingRecord = borrowingRecordService.borrowBook(bookId, patronId);
        return new ResponseEntity<>(borrowingRecord, HttpStatus.CREATED);
    }

    @PutMapping("/return/{bookId}/patron/{patronId}")
    public ResponseEntity<BorrowingRecordDto> returnBook(@PathVariable Long bookId, @PathVariable Long patronId) {
        BorrowingRecordDto borrowingRecord = borrowingRecordService.returnBook(bookId, patronId);
        return new ResponseEntity<>(borrowingRecord, HttpStatus.OK);
    }
}
