package com.example.librarymanagement.repository;

import com.example.librarymanagement.entity.BorrowingRecord;
import com.example.librarymanagement.enums.BorrowingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BorrowingRecordRepository extends JpaRepository<BorrowingRecord,Long> {

    Optional<BorrowingRecord> findByBookIdAndPatronIdAndStatus(Long book_id, Long patron_id, BorrowingStatus status);
}
