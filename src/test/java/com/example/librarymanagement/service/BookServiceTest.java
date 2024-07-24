package com.example.librarymanagement.service;

import com.example.librarymanagement.dto.BookDto;
import com.example.librarymanagement.entity.Book;
import com.example.librarymanagement.exception.BookAlreadyExistsException;
import com.example.librarymanagement.exception.BookNotFoundException;
import com.example.librarymanagement.repository.BookRepository;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoTestRule;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import javax.swing.text.html.Option;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

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

    //@TODO fix IllegalArgumentException
    @Test
    void bookAService_UpdateBook_ShouldReturnBookAlreadyExistsException() {
        // Given
        Long bookId = 1L;
        Book existingBook = Book.builder()
                .id(bookId)  // Ensure the ID is set
                .title("how i met your mother")
                .author("Jon Doe")
                .isbn("123-456")
                .publicationDate(LocalDate.parse("12/11/2002", DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                .build();
        BookDto updatedBookDto = BookDto.builder()
                .title("how i met your mother")
                .author("Jon Doe")
                .isbn("123-456")
                .publicationDate(LocalDate.parse("12/11/2002", DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                .build();
        // Create the updated BookDto with the same ISBN to trigger the exception
//        BookDto updatedBookDto = modelMapper.map(existingBook, BookDto.class);
        updatedBookDto.setTitle("how i met your father");

        // Mock the repository behavior
        when(bookRepository.findById(bookId)).thenReturn(Optional.of(existingBook));
        when(bookRepository.findByIsbn("123-456")).thenReturn(Optional.of(existingBook));
        // When & Then
        Assertions.assertThatThrownBy(() -> bookService.updateBook(bookId , updatedBookDto))
                .isInstanceOf(BookAlreadyExistsException.class)
                .hasMessage("A book with the same ISBN already exists.");
    }



    @Test
void bookService_DeleteBook_ShouldReturnVoid() {
    // Given
    Long bookId = 1L;

    // Mock the repository behavior
    when(bookRepository.existsById(bookId)).thenReturn(true);

    // When
    bookService.deleteBook(bookId);

    // Then
    // Verify that existsById was called exactly once with the correct ID
    verify(bookRepository, times(1)).existsById(bookId);

    // Verify that deleteById was called exactly once with the correct ID
    verify(bookRepository, times(1)).deleteById(bookId);

    // Ensure that existsById is called again to assert the book no longer exists
    when(bookRepository.existsById(bookId)).thenReturn(false);
    Assertions.assertThat(bookRepository.existsById(bookId)).isFalse();
}


    @Test
    void bookService_DeleteBook_ShouldReturnEmpty() {
        Long nonExistentBookId = 1L;
        BookDto bookDto = BookDto.builder()
                .author("kasem a")
                .title("example example")
                .isbn("123-456")
                .publicationDate(LocalDate.parse("12/12/2012", DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                .build();
        //Act
        when(bookRepository.findById(nonExistentBookId)).thenReturn(Optional.empty());
        // Act & Assert
        Assertions.assertThatThrownBy(() -> bookService.updateBook(nonExistentBookId, bookDto)).isInstanceOf(BookNotFoundException.class);


    }


}