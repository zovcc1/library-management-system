package com.example.librarymanagement.service;

import com.example.librarymanagement.dto.PatronDto;
import com.example.librarymanagement.entity.Patron;
import com.example.librarymanagement.exception.NoPatronFoundException;
import com.example.librarymanagement.mapper.PatronMapper;
import com.example.librarymanagement.repository.PatronRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PatronServiceTest {
    @InjectMocks
    private PatronService patronService;
    @Mock
    private PatronRepository patronRepository;
    @Mock
    private PatronMapper patronMapper;


    @BeforeEach
    void setUp() {

    }

    @AfterEach
    void tearDown() {

    }

    @Test
    void patronService_findAllPatrons_ShouldReturnOneOrMorePatronDto() {
        Patron patron = Patron.builder()
                .membershipDate(LocalDate.parse("12/12/2024" , DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                .phoneNumber("0999999999")
                .email("kasem@exmaple.com")
                .firstName("Jon")
                .lastName("Doe")
                .build();
        Patron patron1 = Patron.builder()
                        .firstName("Non")
                                .lastName("Doe")
                                        .email("example@gmail.com")
                                                .phoneNumber("0999999999")
                .membershipDate(LocalDate.parse("12/12/2024" , DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                        .build();
        List<Patron> patrons = Arrays.asList(patron  , patron1);
        PatronDto patronDto = PatronDto.builder()
                    .membershipDate(LocalDate.parse("12/12/2024" , DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                    .phoneNumber("0999999999")
                    .email("kasem@exmaple.com")
                    .firstName("Jon")
                    .lastName("Doe")
                    .build();
            PatronDto patronDto1 = PatronDto.builder()
                    .firstName("Non")
                    .lastName("Doe")
                    .email("example@gmail.com")
                    .phoneNumber("0999999999")
                    .membershipDate(LocalDate.parse("12/12/2024" , DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                    .build();
         List<PatronDto> patronDtos = Arrays.asList(patronDto , patronDto1);

        when(patronRepository.findAll()).thenReturn(patrons);
        when(patronMapper.toDto(patron)).thenReturn(patronDtos.get(0));
         when(patronMapper.toDto(patron1)).thenReturn(patronDtos.get(1));
         List<PatronDto> returnedPatronDtos = patronService.findAllPatrons();
        Assertions.assertThat(returnedPatronDtos).isNotNull();
        Assertions.assertThat(returnedPatronDtos.size()).isEqualTo(2);
    }


    @Test
    void patronService_FindPatronById_ShouldReturnPatronDto() {
        Patron patron = Patron.builder()
                .membershipDate(LocalDate.parse("12/12/2024" , DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                .phoneNumber("0999999999")
                .email("kasem@exmaple.com")
                .firstName("Jon")
                .lastName("Doe")
                .build();
        PatronDto patronDto = PatronDto.builder()
                .membershipDate(LocalDate.parse("12/12/2024" , DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                .phoneNumber("0999999999")
                .email("kasem@exmaple.com")
                .firstName("Jon")
                .lastName("Doe")
                .build();
        when(patronRepository.findById(Mockito.any(Long.class))).thenReturn(Optional.ofNullable(patron));
        when(patronMapper.toDto(patron)).thenReturn(patronDto);
        PatronDto returnedPatronDto = patronService.findPatronById(1L);
        Assertions.assertThat(returnedPatronDto).isNotNull();



    }
    @Test
    void patronService_FindById_ShouldReturnNoPatronFoundException(){

        when(patronRepository.findById(Mockito.any(Long.class))).thenReturn(Optional.empty());
        Assertions.assertThatThrownBy(() -> patronService.findPatronById(Mockito.any(Long.class)))
                .isInstanceOf(NoPatronFoundException.class);

    }

    @Test
    void patronService_AddPatron_ShouldReturnPatronDto() {
        Patron patron = Patron.builder()
                .membershipDate(LocalDate.parse("12/12/2024", DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                .phoneNumber("0999999999")
                .email("kasem@exmaple.com")
                .firstName("Jon")
                .lastName("Doe")
                .build();
        PatronDto patronDto = PatronDto.builder()
                .membershipDate(LocalDate.parse("12/12/2024", DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                .phoneNumber("0999999999")
                .email("kasem@exmaple.com")
                .firstName("Jon")
                .lastName("Doe")
                .build();

        // Mock the repository find method to return an empty Optional
        when(patronRepository.findByPhoneNumberAndEmail(patronDto.getPhoneNumber(), patronDto.getEmail())).thenReturn(Optional.empty());

        when(patronMapper.toEntity(patronDto)).thenReturn(patron);
        when(patronRepository.save(Mockito.any(Patron.class))).thenReturn(patron);
        when(patronMapper.toDto(patron)).thenReturn(patronDto);

        PatronDto returnedPatronDto = patronService.addPatron(patronDto);

        Assertions.assertThat(returnedPatronDto).isNotNull();
        Assertions.assertThat(returnedPatronDto.getLastName()).isEqualTo("Doe");
    }


    @Test
    void updatePatron() {
    }

    @Test
    void deletePatron() {
    }
}