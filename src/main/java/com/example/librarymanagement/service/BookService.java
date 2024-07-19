package com.example.librarymanagement.service;

import com.example.librarymanagement.dto.BookDto;
import com.example.librarymanagement.entity.Book;
import com.example.librarymanagement.exception.BookAlreadyExistsException;
import com.example.librarymanagement.exception.BookNotFoundException;
import com.example.librarymanagement.mapper.BookMapper;
import com.example.librarymanagement.repository.BookRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class BookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    @Autowired
    public BookService(BookRepository bookRepository, BookMapper bookMapper) {
        this.bookRepository = bookRepository;
        this.bookMapper = bookMapper;
    }

    public List<BookDto> findAllBooks() {
        List<Book> books = bookRepository.findAll();
        return bookMapper.toDtoList(books);
    }

    public BookDto findBookById(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException("Book not found with id: " + id));
        return bookMapper.toDto(book);
    }

    public BookDto addBook(BookDto bookDto) {
        Optional<Book> existingBook = bookRepository.findBookByTitleAndIsbn(bookDto.getTitle(), bookDto.getIsbn());
        if (existingBook.isPresent()) {
            throw new BookAlreadyExistsException("Book with the same ISBN and title already exists");
        }
        Book book = bookMapper.toEntity(bookDto);
        Book newBook = bookRepository.save(book);
        return bookMapper.toDto(newBook);
    }

    public BookDto updateBook(Long id, BookDto bookDto) {
        Book existingBook = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException("Book not found with id: " + id));

        // Check if there's another book with the same ISBN
        Optional<Book> bookWithSameIsbn = bookRepository.findByIsbn(bookDto.getIsbn());
        if (bookWithSameIsbn.isPresent() && !bookWithSameIsbn.get().getId().equals(id)) {
            throw new BookAlreadyExistsException("A book with the same ISBN already exists.");
        }

        bookMapper.updateBookFromDto(bookDto, existingBook);

        Book updatedBook = bookRepository.save(existingBook);
        return bookMapper.toDto(updatedBook);
    }

    public void deleteBook(Long id) {
        if (!bookRepository.existsById(id)) {
            throw new BookNotFoundException("Book not found with id: " + id);
        }
        bookRepository.deleteById(id);
    }
}
