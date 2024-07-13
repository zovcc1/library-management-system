package com.example.librarymanagement.dto;

import com.example.librarymanagement.entity.Book;
import com.example.librarymanagement.mapper.BookMapper;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BookDtoTest {

    @Test
    public void testMappingToDto() {
        // Create a sample Book entity
        Book book = new Book();
        book.setId(1L);
        book.setTitle("Sample Book");
        book.setAuthor("John Doe");
        book.setPublicationDate(new Date());
        book.setIsbn("1234567890");

        // Map the entity to DTO
        BookMapper mapper = Mappers.getMapper(BookMapper.class);
        BookDto bookDto = mapper.toDto(book);

        // Assert that the mapping is correct
        assertEquals(book.getId(), bookDto.getId());
        assertEquals(book.getTitle(), bookDto.getTitle());
        assertEquals(book.getAuthor(), bookDto.getAuthor());
        assertEquals(book.getPublicationDate(), bookDto.getPublicationDate());
        assertEquals(book.getIsbn(), bookDto.getIsbn());
    }

    @Test
    public void testMappingToEntity() {
        // Create a sample BookDto
        BookDto bookDto = new BookDto();
        bookDto.setId(1L);
        bookDto.setTitle("Sample Book");
        bookDto.setAuthor("John Doe");
        bookDto.setPublicationDate(new Date());
        bookDto.setIsbn("1234567890");

        // Map the DTO to entity
        BookMapper mapper = Mappers.getMapper(BookMapper.class);
        Book book = mapper.toEntity(bookDto);

        // Assert that the mapping is correct
        assertEquals(bookDto.getId(), book.getId());
        assertEquals(bookDto.getTitle(), book.getTitle());
        assertEquals(bookDto.getAuthor(), book.getAuthor());
        assertEquals(bookDto.getPublicationDate(), book.getPublicationDate());
        assertEquals(bookDto.getIsbn(), book.getIsbn());
    }
}
