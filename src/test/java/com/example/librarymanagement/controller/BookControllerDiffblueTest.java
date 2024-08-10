package com.example.librarymanagement.controller;

import com.example.librarymanagement.dto.BookDto;
import com.example.librarymanagement.exception.BookAlreadyExistsException;
import com.example.librarymanagement.exception.BookNotFoundException;
import com.example.librarymanagement.service.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {BookController.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class BookControllerDiffblueTest {
    @Autowired
    private BookController bookController;

    @MockBean
    private BookService bookService;

    /**
     * Method under test: {@link BookController#findBookById(Long)}
     */
    @Test
    void testFindBookById() throws Exception {
        // Arrange
        BookDto.BookDtoBuilder authorResult = BookDto.builder().author("JaneDoe");
        BookDto buildResult = authorResult.publicationDate(LocalDate.of(1970, 1, 1)).title("Dr").build();
        when(bookService.findBookById(Mockito.<Long>any())).thenReturn(buildResult);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/books/{id}", 1L);

        // Act and Assert
        MockMvcBuilders.standaloneSetup(bookController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string("{\"title\":\"Dr\",\"author\":\"JaneDoe\",\"publicationDate\":\"01/01/1970\",\"isbn\":null}"));
    }

    /**
     * Method under test: {@link BookController#findBookById(Long)}
     */
    @Test
    void testFindBookById2() throws Exception {
        // Arrange
        when(bookService.findBookById(Mockito.<Long>any())).thenThrow(new BookNotFoundException("An error occurred"));
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/books/{id}", 1L);

        // Act
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(bookController).build().perform(requestBuilder);

        // Assert
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    /**
     * Method under test: {@link BookController#addBook(BookDto)}
     */
    @Test
    void testAddBook() throws Exception {
        // Arrange
        BookDto.BookDtoBuilder authorResult = BookDto.builder().author("JaneDoe");
        BookDto buildResult = authorResult.publicationDate(LocalDate.of(1970, 1, 1)).title("Dr").build();
        when(bookService.addBook(Mockito.any())).thenReturn(buildResult);

        BookDto bookDto = new BookDto();
        bookDto.setAuthor("JaneDoe");
        bookDto.setIsbn("Isbn");
        bookDto.setPublicationDate(null);
        bookDto.setTitle("Dr");
        String content = (new ObjectMapper()).writeValueAsString(bookDto);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);

        // Act
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(bookController).build().perform(requestBuilder);

        // Assert
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string("{\"title\":\"Dr\",\"author\":\"JaneDoe\",\"publicationDate\":\"01/01/1970\",\"isbn\":null}"));
    }

    /**
     * Method under test: {@link BookController#addBook(BookDto)}
     */
    @Test
    void testAddBook2() throws Exception {
        // Arrange
        when(bookService.addBook(Mockito.any())).thenThrow(new BookAlreadyExistsException("An error occurred"));

        BookDto bookDto = new BookDto();
        bookDto.setAuthor("JaneDoe");
        bookDto.setIsbn("Isbn");
        bookDto.setPublicationDate(null);
        bookDto.setTitle("Dr");
        String content = (new ObjectMapper()).writeValueAsString(bookDto);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);

        // Act
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(bookController).build().perform(requestBuilder);

        // Assert
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(409));
    }

    /**
     * Method under test: {@link BookController#updateBook(Long, BookDto)}
     */
    @Test
    void testUpdateBook() throws Exception {
        // Arrange
        BookDto.BookDtoBuilder authorResult = BookDto.builder().author("JaneDoe");
        BookDto buildResult = authorResult.publicationDate(LocalDate.of(1970, 1, 1)).title("Dr").build();
        when(bookService.updateBook(Mockito.<Long>any(), Mockito.any())).thenReturn(buildResult);

        BookDto bookDto = new BookDto();
        bookDto.setAuthor("JaneDoe");
        bookDto.setIsbn("Isbn");
        bookDto.setPublicationDate(null);
        bookDto.setTitle("Dr");
        String content = (new ObjectMapper()).writeValueAsString(bookDto);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/api/books/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);

        // Act and Assert
        MockMvcBuilders.standaloneSetup(bookController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string("{\"title\":\"Dr\",\"author\":\"JaneDoe\",\"publicationDate\":\"01/01/1970\",\"isbn\":null}"));
    }

    /**
     * Method under test: {@link BookController#updateBook(Long, BookDto)}
     */
    @Test
    void testUpdateBook2() throws Exception {
        // Arrange
        when(bookService.updateBook(Mockito.<Long>any(), Mockito.any()))
                .thenThrow(new BookNotFoundException("An error occurred"));

        BookDto bookDto = new BookDto();
        bookDto.setAuthor("JaneDoe");
        bookDto.setIsbn("Isbn");
        bookDto.setPublicationDate(null);
        bookDto.setTitle("Dr");
        String content = (new ObjectMapper()).writeValueAsString(bookDto);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/api/books/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);

        // Act
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(bookController).build().perform(requestBuilder);

        // Assert
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    /**
     * Method under test: {@link BookController#updateBook(Long, BookDto)}
     */
    @Test
    void testUpdateBook3() throws Exception {
        // Arrange
        when(bookService.updateBook(Mockito.<Long>any(), Mockito.any()))
                .thenThrow(new BookAlreadyExistsException("An error occurred"));

        BookDto bookDto = new BookDto();
        bookDto.setAuthor("JaneDoe");
        bookDto.setIsbn("Isbn");
        bookDto.setPublicationDate(null);
        bookDto.setTitle("Dr");
        String content = (new ObjectMapper()).writeValueAsString(bookDto);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/api/books/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);

        // Act
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(bookController).build().perform(requestBuilder);

        // Assert
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(409));
    }

    /**
     * Method under test: {@link BookController#deleteBook(Long)}
     */
    @Test
    void testDeleteBook() throws Exception {
        // Arrange
        doNothing().when(bookService).deleteBook(Mockito.<Long>any());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/api/books/{id}", 1L);

        // Act
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(bookController).build().perform(requestBuilder);

        // Assert
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    /**
     * Method under test: {@link BookController#deleteBook(Long)}
     */
    @Test
    void testDeleteBook2() throws Exception {
        // Arrange
        doThrow(new BookNotFoundException("An error occurred")).when(bookService).deleteBook(Mockito.<Long>any());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/api/books/{id}", 1L);

        // Act
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(bookController).build().perform(requestBuilder);

        // Assert
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    /**
     * Method under test: {@link BookController#getAllBooks()}
     */
    @Test
    void testGetAllBooks() throws Exception {
        // Arrange
        when(bookService.findAllBooks()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/books");

        // Act and Assert
        MockMvcBuilders.standaloneSetup(bookController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }
}
