package com.example.librarymanagement.mapper;

import com.example.librarymanagement.dto.BookDto;
import com.example.librarymanagement.entity.Book;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-07-13T11:44:10+0300",
    comments = "version: 1.6.0.Beta1, compiler: javac, environment: Java 17.0.9 (JetBrains s.r.o.)"
)
@Component
public class BookMapperImpl implements BookMapper {

    @Override
    public BookDto toDto(Book book) {
        if ( book == null ) {
            return null;
        }

        BookDto bookDto = new BookDto();

        bookDto.setId( book.getId() );
        bookDto.setTitle( book.getTitle() );
        bookDto.setAuthor( book.getAuthor() );
        bookDto.setPublicationDate( book.getPublicationDate() );
        bookDto.setIsbn( book.getIsbn() );

        return bookDto;
    }

    @Override
    public Book toEntity(BookDto bookDto) {
        if ( bookDto == null ) {
            return null;
        }

        Book book = new Book();

        book.setId( bookDto.getId() );
        book.setTitle( bookDto.getTitle() );
        book.setAuthor( bookDto.getAuthor() );
        book.setPublicationDate( bookDto.getPublicationDate() );
        book.setIsbn( bookDto.getIsbn() );

        return book;
    }

    @Override
    public List<BookDto> toDtoList(List<Book> books) {
        if ( books == null ) {
            return null;
        }

        List<BookDto> list = new ArrayList<BookDto>( books.size() );
        for ( Book book : books ) {
            list.add( toDto( book ) );
        }

        return list;
    }

    @Override
    public void updateBookFromDto(BookDto bookDto, Book existingBook) {
        if ( bookDto == null ) {
            return;
        }

        existingBook.setId( bookDto.getId() );
        existingBook.setTitle( bookDto.getTitle() );
        existingBook.setAuthor( bookDto.getAuthor() );
        existingBook.setPublicationDate( bookDto.getPublicationDate() );
        existingBook.setIsbn( bookDto.getIsbn() );
    }
}
