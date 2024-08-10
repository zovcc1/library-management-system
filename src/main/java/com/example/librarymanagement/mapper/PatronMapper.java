package com.example.librarymanagement.mapper;

import com.example.librarymanagement.dto.PatronDto;
import com.example.librarymanagement.entity.Patron;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PatronMapper {
    Patron toEntity(PatronDto dto);

    PatronDto toDto(Patron entity);

}
