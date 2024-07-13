package com.example.librarymanagement.mapper;

import com.example.librarymanagement.dto.PatronDto;
import com.example.librarymanagement.entity.Patron;
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
public class PatronMapperImpl implements PatronMapper {

    @Override
    public Patron map(PatronDto patronDto) {
        if ( patronDto == null ) {
            return null;
        }

        Patron patron = new Patron();

        patron.setFirstName( patronDto.getFirstName() );
        patron.setLastName( patronDto.getLastName() );
        patron.setEmail( patronDto.getEmail() );
        patron.setPhoneNumber( patronDto.getPhoneNumber() );

        return patron;
    }

    @Override
    public PatronDto map(Patron patron) {
        if ( patron == null ) {
            return null;
        }

        PatronDto patronDto = new PatronDto();

        patronDto.setFirstName( patron.getFirstName() );
        patronDto.setLastName( patron.getLastName() );
        patronDto.setEmail( patron.getEmail() );
        patronDto.setPhoneNumber( patron.getPhoneNumber() );

        return patronDto;
    }

    @Override
    public List<PatronDto> map(List<Patron> patrons) {
        if ( patrons == null ) {
            return null;
        }

        List<PatronDto> list = new ArrayList<PatronDto>( patrons.size() );
        for ( Patron patron : patrons ) {
            list.add( map( patron ) );
        }

        return list;
    }

    @Override
    public void updatePatronFromDto(PatronDto patronDto, Patron existingPatron) {
        if ( patronDto == null ) {
            return;
        }

        existingPatron.setFirstName( patronDto.getFirstName() );
        existingPatron.setLastName( patronDto.getLastName() );
        existingPatron.setEmail( patronDto.getEmail() );
        existingPatron.setPhoneNumber( patronDto.getPhoneNumber() );
    }
}
