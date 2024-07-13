package com.example.librarymanagement.controller;

import com.example.librarymanagement.entity.Book;
import com.example.librarymanagement.entity.BorrowingRecord;
import com.example.librarymanagement.entity.Patron;
import com.example.librarymanagement.service.BorrowingRecordService;
import com.fasterxml.jackson.databind.ObjectMapper;
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

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class BorrowingRecordControllerTest {

    private MockMvc mockMvc;

    @Mock
    private BorrowingRecordService borrowingRecordService;

    @InjectMocks
    private BorrowingRecordController borrowingRecordController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(borrowingRecordController).build();
    }

    @Test
    public void testCreateBorrowingRecord() throws Exception {
        // Given
        Long bookId = 1L;
        Long patronId = 1L;
        Book book = new Book(bookId, "Sample Book", "John Doe", new Date(), "1234567890");
        Patron patron = new Patron("hello", "Doe", "john.doe@example.com", "1234567890", "10/10/2001");

        BorrowingRecord borrowingRecord = new BorrowingRecord();
        borrowingRecord.setId(1L);
        borrowingRecord.setBook(book);
        borrowingRecord.setPatron(patron);
        borrowingRecord.setBorrowDate(new Date());
        borrowingRecord.setStatus("BORROWED");

        doReturn(borrowingRecord).when(borrowingRecordService).borrowBook(eq(bookId), eq(patronId));

        // When
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/borrow/{bookId}/patron/{patronId}", bookId, patronId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        // Then
        String responseContent = result.getResponse().getContentAsString();
        ObjectMapper objectMapper = new ObjectMapper();
        BorrowingRecord responseBorrowingRecord = objectMapper.readValue(responseContent, BorrowingRecord.class);
        assertEquals("BORROWED", responseBorrowingRecord.getStatus());
    }

    @Test
    public void testReturnBook() throws Exception {
        // Given
        Long bookId = 1L;
        Long patronId = 1L;
        Book book = new Book(bookId, "Sample Book", "John Doe", new Date(), "1234567890");
        Patron patron = new Patron(patronId , "kasem" , "alachikh ali" , "qassemmohammedali@gmail.com" , "1233445","10/10/2020");

        BorrowingRecord borrowingRecord = new BorrowingRecord();
        borrowingRecord.setId(1L);
        borrowingRecord.setBook(book);
        borrowingRecord.setPatron(patron);
        borrowingRecord.setBorrowDate(new Date());
        borrowingRecord.setStatus("RETURNED");

        doReturn(borrowingRecord).when(borrowingRecordService).returnBook(eq(bookId), eq(patronId));

        // When
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put("/api/return/{bookId}/patron/{patronId}", bookId, patronId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        // Then
        String responseContent = result.getResponse().getContentAsString();
        ObjectMapper objectMapper = new ObjectMapper();
        BorrowingRecord responseBorrowingRecord = objectMapper.readValue(responseContent, BorrowingRecord.class);
        assertEquals("RETURNED", responseBorrowingRecord.getStatus());
    }
}
