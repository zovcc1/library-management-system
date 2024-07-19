package com.example.librarymanagement.mapper;

import com.example.librarymanagement.dto.BorrowingRecordDto;
import com.example.librarymanagement.entity.BorrowingRecord;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BorrowingRecordMapper {

    @Mapping(source = "patron.id", target = "patronId")
    @Mapping(source = "book.id", target = "bookId")
    BorrowingRecordDto toDto(BorrowingRecord borrowingRecord);

    @Mapping(source = "patronId", target = "patron.id")
    @Mapping(source = "bookId", target = "book.id")
    BorrowingRecord toEntity(BorrowingRecordDto borrowingRecordDto);
}
