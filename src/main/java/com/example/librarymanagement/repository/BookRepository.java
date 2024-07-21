package com.example.librarymanagement.repository;

import com.example.librarymanagement.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    //    List<Book> findAll();
//    Optional<Book> findBookById(Long id);
    Optional<Book> findBookByTitleAndIsbn(String title, String isbn);


    Book getBookById(Long id);

    Optional<Book> findByIsbn(String isbn);
}
