package com.example.librarymanagement.controller;

import com.example.librarymanagement.dto.BookDto;
import com.example.librarymanagement.entity.Book;
import com.example.librarymanagement.exception.BookAlreadyExistsException;
import com.example.librarymanagement.exception.BookNotFoundException;
import com.example.librarymanagement.mapper.BookMapper;
import com.example.librarymanagement.service.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {BookController.class})
@DisabledInAotMode
@ExtendWith(MockitoExtension.class)
class BookControllerTest {

    @Mock
    private BookService bookService;

    @Mock
    private BookMapper bookMapper;

    @InjectMocks
    private BookController bookController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;
    private BookDto bookDto;
    private Book book;

    @BeforeEach
    void setUp() {
        book = Book.builder()
                .id(1L)
                .title("GOT")
                .author("Jon")
                .isbn("isbn")
                .publicationDate(LocalDate.parse("12/12/2001", DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                .build();

        bookDto = BookDto.builder()
                .title("GOT")
                .author("Jon")
                .isbn("isbn")
                .publicationDate(LocalDate.parse("12/12/2001", DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                .build();

        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule()); // Registering the JavaTimeModule

        mockMvc = MockMvcBuilders.standaloneSetup(bookController).build();
    }

    @Test
    void bookController_FindBookById_ShouldReturnFound() throws Exception {
        // Arrange
        when(bookService.findBookById(any(Long.class))).thenReturn(bookDto);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/books/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(bookDto));

        // Act & Assert
        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void bookController_FindBookById_ShouldReturnNotFound() throws Exception {
        // Arrange
        when(bookService.findBookById(any(Long.class)))
                .thenThrow(new BookNotFoundException("book with id not found"));
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/books/{id}", 2L)
                .contentType(MediaType.APPLICATION_JSON);

        // Act & Assert
        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void bookController_AddBook_ShouldReturnCreated() throws Exception {
        // Arrange
        when(bookService.addBook(Mockito.any())).thenReturn(bookDto);

        String content = objectMapper.writeValueAsString(bookDto);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);

        // Act & Assert
        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void bookController_AddBook_ShouldReturnStatusConflict() throws Exception {
        // Arrange
        when(bookService.addBook(Mockito.any())).thenThrow(new BookAlreadyExistsException("An error occurred"));

        String content = objectMapper.writeValueAsString(bookDto);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);

        // Act & Assert
        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isConflict());
    }

    @Test
    void bookController_UpdateBook_ShouldReturnOK() throws Exception {
        // Arrange
        when(bookService.updateBook(Mockito.any(), Mockito.any())).thenReturn(bookDto);

        String content = objectMapper.writeValueAsString(bookDto);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/api/books/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);

        // Act & Assert
        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().string(content));
    }

    @Test
    void bookController_UpdateBook_ShouldReturnNotFound() throws Exception {
        // Arrange
        when(bookService.updateBook(Mockito.any(), Mockito.any())).thenThrow(new BookNotFoundException("An error occurred"));

        String content = objectMapper.writeValueAsString(bookDto);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/api/books/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);

        // Act & Assert
        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void bookController_UpdateBook_ShouldReturnConflict() throws Exception {
        // Arrange
        when(bookService.updateBook(Mockito.any(), Mockito.any())).thenThrow(new BookAlreadyExistsException("Book with same id exists"));

        String content = objectMapper.writeValueAsString(bookDto);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/api/books/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);

        // Act & Assert
        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isConflict());
    }

    @Test
    void bookController_DeleteBook_ShouldReturnNoContent() throws Exception {
        // Arrange
        doNothing().when(bookService).deleteBook(Mockito.<Long>any());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/api/books/{id}", 1L);

        // Act & Assert
        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    void bookController_DeleteBook_ShouldReturnNotFound() throws Exception {
        // Arrange
        doThrow(new BookNotFoundException("An error occurred")).when(bookService).deleteBook(Mockito.<Long>any());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/api/books/{id}", 1L);

        // Act & Assert
        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}
