package com.example.librarymanagement.service;

import com.example.librarymanagement.dto.BookDto;
import com.example.librarymanagement.entity.Book;
import com.example.librarymanagement.exception.BookAlreadyExistsException;
import com.example.librarymanagement.exception.BookNotFoundException;
import com.example.librarymanagement.mapper.BookMapper;
import com.example.librarymanagement.repository.BookRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class BookService {

    //    private static final Logger log = LoggerFactory.getLogger(BookService.class);
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    public BookService(BookRepository bookRepository, BookMapper bookMapper) {
        this.bookRepository = bookRepository;
        this.bookMapper = bookMapper;

    }


    public List<BookDto> findAllBooks() {
        List<Book> books = bookRepository.findAll();
        return books.stream()
                .map(bookMapper::toDto)
                .collect(Collectors.toList());
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
        return bookMapper.toDto(book);
    }

    public BookDto updateBook(Long id, BookDto bookDto) {
        // Step 1: Validate input parameters
        if (id == null || bookDto == null) {
            throw new IllegalArgumentException("Invalid input parameters.");
        }

        // Step 2: Find the existing book by ID
        Book existingBook = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException("Book not found with id: " + id));

        // Step 3: Check if there's another book with the same ISBN
        Optional<Book> bookWithSameIsbn = bookRepository.findByIsbn(bookDto.getIsbn());
        if (bookWithSameIsbn.isPresent() && !bookWithSameIsbn.get().getId().equals(id)) {
            throw new BookAlreadyExistsException("A book with the same ISBN already exists.");
        }

        // Step 4: Update the existing book fields
        existingBook.setTitle(bookDto.getTitle());
        existingBook.setAuthor(bookDto.getAuthor());
        existingBook.setIsbn(bookDto.getIsbn());
        existingBook.setPublicationDate(bookDto.getPublicationDate());
        Book updatedBook = bookRepository.save(existingBook);
        // Step 5: Save the updated book

        // Step 6: Map the updated book to a DTO and return it
        return bookMapper.toDto(updatedBook);
    }


    public void deleteBook(Long id) {
        if (!bookRepository.existsById(id)) {
            throw new BookNotFoundException("Book not found with id: " + id);
        }
        bookRepository.deleteById(id);
    }
}
