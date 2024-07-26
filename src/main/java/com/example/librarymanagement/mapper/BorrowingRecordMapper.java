package com.example.librarymanagement.mapper;

import com.example.librarymanagement.dto.BorrowingRecordDto;
import com.example.librarymanagement.entity.BorrowingRecord;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BorrowingRecordMapper {
    BorrowingRecord toEntity(BorrowingRecordDto Dto);
    BorrowingRecordDto toDto(BorrowingRecord entity);
}
