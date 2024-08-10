package com.example.librarymanagement.controller;

import com.example.librarymanagement.dto.BorrowingRecordDto;
import com.example.librarymanagement.service.BorrowingRecordService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;

import static org.mockito.Mockito.when;

@ContextConfiguration(classes = {BorrowingRecordController.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class BorrowingRecordControllerTest {
    @Autowired
    private BorrowingRecordController borrowingRecordController;

    @MockBean
    private BorrowingRecordService borrowingRecordService;

    /**
     * Method under test: {@link BorrowingRecordController#borrowBook(Long, Long)}
     */
    @Test
    void testBorrowBook() throws Exception {
        // Arrange
        BorrowingRecordDto.BorrowingRecordDtoBuilder builderResult = BorrowingRecordDto.builder();
        BorrowingRecordDto.BorrowingRecordDtoBuilder borrowDateResult = builderResult.borrowDate(LocalDate.of(1970, 1, 1));
        BorrowingRecordDto buildResult = borrowDateResult.returnDate(LocalDate.of(1970, 1, 1)).status("Status").build();
        when(borrowingRecordService.borrowBook(Mockito.<Long>any(), Mockito.<Long>any())).thenReturn(buildResult);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/borrow/{bookId}/patron/{patronId}",
                1L, 1L);

        // Act
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(borrowingRecordController)
                .build()
                .perform(requestBuilder);

        // Assert
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string("{\"borrowDate\":\"01/01/1970\",\"returnDate\":\"01/01/1970\",\"status\":\"Status\"}"));
    }

    /**
     * Method under test: {@link BorrowingRecordController#returnBook(Long, Long)}
     */
    @Test
    void testReturnBook() throws Exception {
        // Arrange
        BorrowingRecordDto.BorrowingRecordDtoBuilder builderResult = BorrowingRecordDto.builder();
        BorrowingRecordDto.BorrowingRecordDtoBuilder borrowDateResult = builderResult.borrowDate(LocalDate.of(1970, 1, 1));
        BorrowingRecordDto buildResult = borrowDateResult.returnDate(LocalDate.of(1970, 1, 1)).status("Status").build();
        when(borrowingRecordService.returnBook(Mockito.<Long>any(), Mockito.<Long>any())).thenReturn(buildResult);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/api/return/{bookId}/patron/{patronId}",
                1L, 1L);

        // Act and Assert
        MockMvcBuilders.standaloneSetup(borrowingRecordController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string("{\"borrowDate\":\"01/01/1970\",\"returnDate\":\"01/01/1970\",\"status\":\"Status\"}"));
    }
}
