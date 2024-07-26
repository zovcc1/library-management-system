package com.example.librarymanagement.mapper;

import com.example.librarymanagement.dto.BookDto;
import com.example.librarymanagement.entity.Book;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BookMapper {

   Book toEntity(BookDto bookDto);
   BookDto toDto(Book book);
}
