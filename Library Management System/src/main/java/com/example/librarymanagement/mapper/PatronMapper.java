package com.example.librarymanagement.mapper;

import com.example.librarymanagement.dto.PatronDto;
import com.example.librarymanagement.entity.Patron;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PatronMapper {
Patron map(PatronDto patronDto);
PatronDto map(Patron patron);
List<PatronDto> map(List<Patron> patrons);


    void updatePatronFromDto(PatronDto patronDto,@MappingTarget Patron existingPatron);
}
