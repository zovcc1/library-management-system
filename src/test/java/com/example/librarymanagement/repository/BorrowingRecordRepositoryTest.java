package com.example.librarymanagement.repository;

import com.example.librarymanagement.entity.Book;
import com.example.librarymanagement.entity.BorrowingRecord;
import com.example.librarymanagement.entity.Patron;
import com.example.librarymanagement.enums.BorrowingStatus;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@DataJpaTest
class BorrowingRecordRepositoryTest {
    @Autowired
    private BorrowingRecordRepository borrowingRecordRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private PatronRepository patronRepository;

    private Book borrowedBook;
    private Patron patron;
    private BorrowingRecord borrowingRecord;

    @BeforeEach
    void setUp() {
        borrowedBook = new Book();
        borrowedBook.setTitle("Friends");
        borrowedBook.setAuthor("Jon");
        borrowedBook.setPublicationDate(LocalDate.of(2010, 1, 1));
        borrowedBook.setIsbn("123-456");
        bookRepository.save(borrowedBook);

        patron = new Patron();
        patron.setEmail("email@gmaiil.com");
        patron.setFirstName("kasem");
        patron.setLastName("alchikh ali");
        patron.setPhoneNumber("+963991991333");
        patron.setMembershipDate(LocalDate.of(2010, 1, 1));
        patronRepository.save(patron);

        borrowingRecord = new BorrowingRecord();
        borrowingRecord.setBorrowDate(LocalDate.of(2024, 1, 1));
        borrowingRecord.setStatus(BorrowingStatus.BORROWED);
        borrowingRecord.setReturnDate(LocalDate.of(2024, 2, 2));
        borrowingRecord.setBook(borrowedBook);
        borrowingRecord.setPatron(patron);
        borrowingRecordRepository.save(borrowingRecord);
    }

    @AfterEach
    void tearDown() {
        borrowingRecordRepository.deleteAll();
        bookRepository.deleteAll();
        patronRepository.deleteAll();
    }

    @Test
    void shouldFindByBookIdAndPatronIdAndStatus_thenReturnBorrowingRecord() {
        // given
        Long bookId = borrowedBook.getId();
        Long patronId = patron.getId();

        // when
        Optional<BorrowingRecord> foundRecord = borrowingRecordRepository.findByBookIdAndPatronIdAndStatus(bookId, patronId, BorrowingStatus.BORROWED);

        // then
        assertThat(foundRecord.isPresent(), is(true));
        assertThat(foundRecord.get().getId(), is(borrowingRecord.getId()));
    }
}
