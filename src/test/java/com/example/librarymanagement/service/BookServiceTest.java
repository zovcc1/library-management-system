package com.example.librarymanagement.service;

import com.example.librarymanagement.dto.BookDto;
import com.example.librarymanagement.entity.Book;
import com.example.librarymanagement.mapper.BookMapper;
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
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
@ExtendWith(MockitoExtension.class)
class BookServiceTest {
    @Mock
    private BookRepository bookRepository;
    @InjectMocks
    private BookService bookService;
    @Mock
    private BookMapper bookMapper;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void BookService_CreateBook_ShouldReturnBookDto(){
        Book book  = Book.builder()
                .title("how i met your mother")
                .author("Jon Doe")
                .isbn("123-456")
                .publicationDate(LocalDate.parse("12/11/2002" , DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                .build();
        BookDto bookDto = BookDto.builder()
                .title("how i met your mother")
                .author("Jon Doe")
                .isbn("123-456")
                .publicationDate(LocalDate.parse("12/11/2002" , DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                .build();

        when(bookMapper.toEntity(bookDto)).thenReturn(book);
        when(bookRepository.save(Mockito.any(Book.class))).thenReturn(book);
        when(bookMapper.toDto(book)).thenReturn(bookDto);

        BookDto savedBook = bookService.addBook(bookDto);
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
        List<BookDto> bookDtos = Arrays.asList(
                BookDto.builder()
                        .title("how i met your mother")
                        .author("Jon Doe")
                        .isbn("123-456")
                        .publicationDate(LocalDate.parse("12/11/2002", DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                        .build(),
                BookDto.builder()
                        .title("friends")
                        .author("Jane Doe")
                        .isbn("789-012")
                        .publicationDate(LocalDate.parse("15/10/2003", DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                        .build()
        );

        when(bookRepository.findAll()).thenReturn(books);
        when(bookMapper.toDto(book1)).thenReturn(bookDtos.get(0));
        when(bookMapper.toDto(book2)).thenReturn(bookDtos.get(1));

        List<BookDto> returnedBookDtos = bookService.findAllBooks();
        Assertions.assertThat(returnedBookDtos).isNotNull();
        Assertions.assertThat(returnedBookDtos.size()).isEqualTo(2);
        Assertions.assertThat(returnedBookDtos.get(0).getTitle()).isEqualTo("how i met your mother");
        Assertions.assertThat(returnedBookDtos.get(1).getTitle()).isEqualTo("friends");
    }

    @Test
    void BookService_UpdateBook_ShouldReturnBookDto(){
        Book existingBook = Book.builder()
                .id(1L)
                .title("how i met your mother")
                .author("Jon Doe")
                .isbn("123-456")
                .publicationDate(LocalDate.parse("12/11/2002", DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                .build();
        BookDto updatedBookDto = BookDto.builder()
                .title("how i met your father")
                .author("Jon Doe")
                .isbn("123-456")
                .publicationDate(LocalDate.parse("12/12/2002" , DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                .build();
        Book updatedBook = Book.builder()
                .id(1L)
                .title("how i met your father")
                .author("Jon Doe")
                .isbn("123-456")
                .publicationDate(LocalDate.parse("12/12/2002", DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                .build();

        when(bookRepository.findById(1L)).thenReturn(Optional.of(existingBook));
        when(bookRepository.save(Mockito.any(Book.class))).thenReturn(updatedBook);
        when(bookMapper.toDto(updatedBook)).thenReturn(updatedBookDto);

        BookDto returnedBook = bookService.updateBook(1L, updatedBookDto);
        Assertions.assertThat(returnedBook).isNotNull();
        Assertions.assertThat(returnedBook.getTitle()).isEqualTo("how i met your father");
    }

    @Test
    void bookService_DeleteBook_ShouldReturnEmpty(){
        // Mock the behavior to simulate the book exists
        when(bookRepository.existsById(1L)).thenReturn(true);

        // Call the delete method
        bookService.deleteBook(1L);

        // Verify that deleteById was called
        Mockito.verify(bookRepository, Mockito.times(1)).deleteById(1L);
    }

}
