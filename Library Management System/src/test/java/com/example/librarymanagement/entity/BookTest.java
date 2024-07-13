package com.example.librarymanagement;

import com.example.librarymanagement.controller.BookController;
import com.example.librarymanagement.dto.BookDto;
import com.example.librarymanagement.entity.Book;
import com.example.librarymanagement.exception.BookAlreadyExistsException;
import com.example.librarymanagement.exception.BookNotFoundException;
import com.example.librarymanagement.service.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static java.util.Optional.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class BookTest {

    private MockMvc mockMvc;

    @Mock
    private BookService bookService;

    @InjectMocks
    private BookController bookController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(bookController).build();
    }


    @Test
    public void testGetAllBooks() throws Exception {
        // Given
        List<Book> books = new ArrayList<>();
        books.add(new Book(1L, "Book 1", "Author 1", new Date(), "1234567890"));
        books.add(new Book(2L, "Book 2", "Author 2", new Date(), "2345678901"));

        when(bookService.findAllBooks()).thenReturn(books);

        // When
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/api/books")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        // Then
        ObjectMapper objectMapper = new ObjectMapper();
        String responseContent = result.getResponse().getContentAsString();
        List<Book> responseBooks = objectMapper.readValue(responseContent, objectMapper.getTypeFactory().constructCollectionType(List.class, Book.class));
        assertEquals(2, responseBooks.size());
        assertEquals("Book 1", responseBooks.get(0).getTitle());
        assertEquals("Author 1", responseBooks.get(0).getAuthor());
        assertEquals("Book 2", responseBooks.get(1).getTitle());
        assertEquals("Author 2", responseBooks.get(1).getAuthor());
    }

    @Test
    public void testAddBook() throws Exception {
        // Given
        BookDto bookDto = new BookDto("New Book", "New Author", new Date(), "9876543210");
        Book createdBook = new Book(1L, bookDto.getTitle(), bookDto.getAuthor(), bookDto.getPublicationDate(), bookDto.getIsbn());

        when(bookService.addBook(any(BookDto.class))).thenReturn(createdBook);

        // When
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/books")
                        .content(asJsonString(bookDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();

        // Then
        ObjectMapper objectMapper = new ObjectMapper();
        String responseContent = result.getResponse().getContentAsString();
        Book responseBook = objectMapper.readValue(responseContent, Book.class);
        assertEquals("New Book", responseBook.getTitle());
        assertEquals("New Author", responseBook.getAuthor());
        assertEquals("9876543210", responseBook.getIsbn());
    }

    @Test
    public void testUpdateBook() throws Exception {
        // Given
        Long bookId = 1L;
        BookDto bookDto = new BookDto("Updated Book", "Updated Author", new Date(), "9876543210");
        Book updatedBook = new Book(bookId, bookDto.getTitle(), bookDto.getAuthor(), bookDto.getPublicationDate(), bookDto.getIsbn());

        when(bookService.updateBook(eq(bookId), any(BookDto.class))).thenReturn(of(updatedBook));

        // When
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put("/api/books/{id}", bookId)
                        .content(asJsonString(bookDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        // Then
        ObjectMapper objectMapper = new ObjectMapper();
        String responseContent = result.getResponse().getContentAsString();
        Book responseBook = objectMapper.readValue(responseContent, Book.class);
        assertEquals("Updated Book", responseBook.getTitle());
        assertEquals("Updated Author", responseBook.getAuthor());
        assertEquals("9876543210", responseBook.getIsbn());
    }

    @Test
    public void testDeleteBook() throws Exception {
        // Given
        Long bookId = 1L;

        // When
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.delete("/api/books/{id}", bookId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        // Then
        verify(bookService, times(1)).deleteBook(bookId);
    }

    @Test
    public void testGetBookById() throws Exception {
        // Given
        Long bookId = 1L;
        Book book = new Book(bookId, "Book 1", "Author 1", new Date(), "1234567890");

        when(bookService.findBookById(1L)).thenReturn(of(book));

        // When
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/api/books/{id}", bookId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        // Then
        ObjectMapper objectMapper = new ObjectMapper();
        String responseContent = result.getResponse().getContentAsString();
        Book responseBook = objectMapper.readValue(responseContent, Book.class);
        assertEquals("Book 1", responseBook.getTitle());
        assertEquals("Author 1", responseBook.getAuthor());
        assertEquals("1234567890", responseBook.getIsbn());
    }

    // Utility method to convert object to JSON string
    private String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
