package com.example.librarymanagement.controller;

import com.example.librarymanagement.dto.BookDto;
import com.example.librarymanagement.entity.Book;
import com.example.librarymanagement.exception.BookAlreadyExistsException;
import com.example.librarymanagement.exception.BookNotFoundException;
import com.example.librarymanagement.service.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class BookControllerTest {

    private MockMvc mockMvc;

    @Mock
    private BookService bookService;

    @InjectMocks
    private BookController bookController;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(bookController).build();
    }

    @Test
    public void testGetAllBooks() throws Exception {
        // Mocking the service layer
        List<Book> books = Arrays.asList(
                new Book(1L, "Book 1", "Author 1", new Date(), "1234567890"),
                new Book(2L, "Book 2", "Author 2", new Date(), "2345678901"));
        doReturn(books).when(bookService).findAllBooks();

        // Performing the GET request
        mockMvc.perform(MockMvcRequestBuilders.get("/api/books")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()").value(2)); // Assuming the response is a JSON array with 2 elements
    }

    @Test
    public void testFindBookById() throws Exception {
        // Mocking the service layer
        Book book = new Book(1L, "Book 1", "Author 1", new Date(), "1234567890");
        doReturn(book).when(bookService).findBookById(1L);

        // Performing the GET request
        mockMvc.perform(MockMvcRequestBuilders.get("/api/books/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("Book 1"));
    }

    @Test
    public void testFindBookById_NotFound() throws Exception {
        // Mocking the service layer to throw BookNotFoundException
        doThrow(new BookNotFoundException("Book not found")).when(bookService).findBookById(anyLong());

        // Performing the GET request
        mockMvc.perform(MockMvcRequestBuilders.get("/api/books/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testAddBook() throws Exception {
        // Mocking the service layer
        BookDto bookDto = new BookDto(null, "New Book", "New Author", new Date(), "0987654321");
        Book createdBook = new Book(1L, "New Book", "New Author", new Date(), "0987654321");
        doReturn(createdBook).when(bookService).addBook(any(BookDto.class));

        // Performing the POST request
        mockMvc.perform(MockMvcRequestBuilders.post("/api/books")
                        .content(new ObjectMapper().writeValueAsString(bookDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("New Book"));
    }

    @Test
    public void testAddBook_Conflict() throws Exception {
        // Mocking the service layer to throw BookAlreadyExistsException
        BookDto bookDto = new BookDto(null, "New Book", "New Author", new Date(), "0987654321");
        doThrow(new BookAlreadyExistsException("Book already exists")).when(bookService).addBook(any(BookDto.class));

        // Performing the POST request
        mockMvc.perform(MockMvcRequestBuilders.post("/api/books")
                        .content(new ObjectMapper().writeValueAsString(bookDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict());
    }

    @Test
    public void testUpdateBook() throws Exception {
        // Mocking the service layer
        BookDto bookDto = new BookDto(null, "Updated Book", "Updated Author", new Date(), "1234567890");
        Book updatedBook = new Book(1L, "Updated Book", "Updated Author", new Date(), "1234567890");
        doReturn(updatedBook).when(bookService).updateBook(eq(1L), any(BookDto.class));

        // Performing the PUT request
        mockMvc.perform(MockMvcRequestBuilders.put("/api/books/1")
                        .content(new ObjectMapper().writeValueAsString(bookDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("Updated Book"));
    }

    @Test
    public void testUpdateBook_NotFound() throws Exception {
        // Mocking the service layer to throw BookNotFoundException
        BookDto bookDto = new BookDto(null, "Updated Book", "Updated Author", new Date(), "1234567890");
        doThrow(new BookNotFoundException("Book not found")).when(bookService).updateBook(eq(1L), any(BookDto.class));

        // Performing the PUT request
        mockMvc.perform(MockMvcRequestBuilders.put("/api/books/1")
                        .content(new ObjectMapper().writeValueAsString(bookDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testDeleteBook() throws Exception {
        // Performing the DELETE request
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/books/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
