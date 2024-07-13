package com.example.librarymanagement.mapper;

import com.example.librarymanagement.dto.BorrowingRecordDto;
import com.example.librarymanagement.entity.Book;
import com.example.librarymanagement.entity.BorrowingRecord;
import com.example.librarymanagement.entity.Patron;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-07-13T11:44:10+0300",
    comments = "version: 1.6.0.Beta1, compiler: javac, environment: Java 17.0.9 (JetBrains s.r.o.)"
)
@Component
public class BorrowingRecordMapperImpl implements BorrowingRecordMapper {

    @Override
    public BorrowingRecordDto toDto(BorrowingRecord borrowingRecord) {
        if ( borrowingRecord == null ) {
            return null;
        }

        BorrowingRecordDto borrowingRecordDto = new BorrowingRecordDto();

        borrowingRecordDto.setPatronId( borrowingRecordPatronId( borrowingRecord ) );
        borrowingRecordDto.setBookId( borrowingRecordBookId( borrowingRecord ) );
        borrowingRecordDto.setId( borrowingRecord.getId() );
        borrowingRecordDto.setBorrowDate( borrowingRecord.getBorrowDate() );
        borrowingRecordDto.setReturnDate( borrowingRecord.getReturnDate() );
        borrowingRecordDto.setStatus( borrowingRecord.getStatus() );

        return borrowingRecordDto;
    }

    @Override
    public BorrowingRecord toEntity(BorrowingRecordDto borrowingRecordDto) {
        if ( borrowingRecordDto == null ) {
            return null;
        }

        BorrowingRecord borrowingRecord = new BorrowingRecord();

        borrowingRecord.setPatron( borrowingRecordDtoToPatron( borrowingRecordDto ) );
        borrowingRecord.setBook( borrowingRecordDtoToBook( borrowingRecordDto ) );
        borrowingRecord.setId( borrowingRecordDto.getId() );
        borrowingRecord.setBorrowDate( borrowingRecordDto.getBorrowDate() );
        borrowingRecord.setReturnDate( borrowingRecordDto.getReturnDate() );
        borrowingRecord.setStatus( borrowingRecordDto.getStatus() );

        return borrowingRecord;
    }

    private Long borrowingRecordPatronId(BorrowingRecord borrowingRecord) {
        Patron patron = borrowingRecord.getPatron();
        if ( patron == null ) {
            return null;
        }
        return patron.getId();
    }

    private Long borrowingRecordBookId(BorrowingRecord borrowingRecord) {
        Book book = borrowingRecord.getBook();
        if ( book == null ) {
            return null;
        }
        return book.getId();
    }

    protected Patron borrowingRecordDtoToPatron(BorrowingRecordDto borrowingRecordDto) {
        if ( borrowingRecordDto == null ) {
            return null;
        }

        Patron patron = new Patron();

        patron.setId( borrowingRecordDto.getPatronId() );

        return patron;
    }

    protected Book borrowingRecordDtoToBook(BorrowingRecordDto borrowingRecordDto) {
        if ( borrowingRecordDto == null ) {
            return null;
        }

        Book book = new Book();

        book.setId( borrowingRecordDto.getBookId() );

        return book;
    }
}
