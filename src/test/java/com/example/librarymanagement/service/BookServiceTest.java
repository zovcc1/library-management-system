package com.example.librarymanagement.service;

import com.example.librarymanagement.dto.BookDto;
import com.example.librarymanagement.entity.Book;
import com.example.librarymanagement.repository.BookRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {
    @Mock
    private BookRepository bookRepository;
    @InjectMocks
    private BookService bookService;

    private ModelMapper modelMapper;

    @BeforeEach
    void setUp() {
        modelMapper = new ModelMapper();
        bookService = new BookService(bookRepository, modelMapper);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void BookService_CreateBook_ShouldReturnBookDto() {
        Book book = Book.builder()
                .title("how i met your mother")
                .author("Jon Doe")
                .isbn("123-456")
                .publicationDate(LocalDate.parse("12/11/2002", DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                .build();
        BookDto bookDto = BookDto.builder().title("how i met your mother").author("Jon Doe").isbn("123-456").publicationDate(LocalDate.parse("12/11/2002", DateTimeFormatter.ofPattern("dd/MM/yyyy"))).build();
        when(bookRepository.save(Mockito.any(Book.class))).thenReturn(book);
        BookDto savedBook = bookService.addBook(modelMapper.map(book, BookDto.class));
        Assertions.assertThat(savedBook).isNotNull();


    }

    @Test
    void BookService_FindAllBooks_ShouldReturnListOfBookDto() {
        Book book1 = Book.builder()
                .title("how i met your mother")
                .author("Jon Doe")
                .isbn("123-456")
                .publicationDate(LocalDate.parse("12/11/2002", DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                .build();

        Book book2 = Book.builder()
                .title("friends")
                .author("Jane Doe")
                .isbn("789-012")
                .publicationDate(LocalDate.parse("15/10/2003", DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                .build();

        List<Book> books = Arrays.asList(book1, book2);
        when(bookRepository.findAll()).thenReturn(books);

        List<BookDto> bookDtos = bookService.findAllBooks();
        Assertions.assertThat(bookDtos).isNotNull();
        Assertions.assertThat(bookDtos.size()).isEqualTo(2);
        Assertions.assertThat(bookDtos.get(0).getTitle()).isEqualTo("how i met your mother");
        Assertions.assertThat(bookDtos.get(1).getTitle()).isEqualTo("friends");
    }

    @Test
    void BookService_UpdateBook_ShouldReturnBookDto() {
        Book existingBook = Book.builder()
                .id(1L)
                .title("how i met your mother")
                .author("Jon Doe")
                .isbn("123-456")
                .publicationDate(LocalDate.parse("12/11/2002", DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                .build();
        BookDto updatedBook = BookDto.builder()
                .title("how i met your father")
                .author("Jon Doe")
                .isbn("123-456")
                .publicationDate(LocalDate.parse("12/12/2002", DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                .build();
        when(bookRepository.findById(1L)).thenReturn(Optional.ofNullable(existingBook));
        when(bookRepository.findByIsbn("123-456")).thenReturn(Optional.ofNullable(existingBook));
        when(bookRepository.save(Mockito.any(Book.class))).thenReturn(existingBook);
        BookDto returnedBook = bookService.updateBook(1L, updatedBook);
        Assertions.assertThat(returnedBook).isNotNull();
        Assertions.assertThat(returnedBook.getTitle()).isEqualTo("how i met your father");

    }


    @Test
    void bookService_DeleteBook_ShouldReturnEmpty() {
        Book book = Book.builder()
                .title("friends")
                .author("Jane Doe")
                .isbn("789-012")
                .publicationDate(LocalDate.parse("15/10/2003", DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                .build();
        when(bookRepository.findById(1L)).thenReturn(Optional.ofNullable(book));
        assertAll(() -> bookRepository.deleteById(1L));


    }


}