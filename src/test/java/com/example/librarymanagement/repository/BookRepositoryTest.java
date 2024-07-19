package com.example.librarymanagement.repository;

import com.example.librarymanagement.entity.Book;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    @BeforeEach
    public void setUp() {
        Book mockBook = new Book();
        mockBook.setAuthor("kasem");
        mockBook.setTitle("how i met your mother");
        mockBook.setPublicationDate(LocalDate.of(1999, 1, 1));
        mockBook.setIsbn("1234");
        bookRepository.save(mockBook);
    }

    @AfterEach
    void tearDown() {
        bookRepository.deleteAll();
    }

    @Test
    void shouldFindBookByTitleAndIsbn() {
        // given
        String title = "how i met your mother";
        String isbn = "1234";

        // when
        Book foundBook = bookRepository.findBookByTitleAndIsbn(title, isbn).orElseThrow();

        // then
        assertEquals(isbn, foundBook.getIsbn());
        assertEquals(title , foundBook.getTitle());

    }

    @Test
    void findByIsbn() {
        // given
          String isbn = "1234" ;
          //when
        Book existingBook  = bookRepository.findByIsbn(isbn).orElseThrow();
        //then
        assertEquals(isbn , existingBook.getIsbn());

    }
}
