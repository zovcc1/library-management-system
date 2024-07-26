package com.example.librarymanagement.service;

import com.example.librarymanagement.dto.PatronDto;
import com.example.librarymanagement.entity.Patron;
import com.example.librarymanagement.exception.NoPatronFoundException;
import com.example.librarymanagement.exception.PatronAlreadyExistsException;
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

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PatronServiceTest {
    @InjectMocks
    private PatronService patronService;
    @Mock
    private PatronRepository patronRepository;
    @Mock
    private PatronMapper patronMapper;


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
//        when(patronRepository.findByPhoneNumberAndEmail(patronDto.getPhoneNumber(), patronDto.getEmail())).thenReturn(Optional.empty());

        when(patronMapper.toEntity(patronDto)).thenReturn(patron);
        when(patronRepository.save(Mockito.any(Patron.class))).thenReturn(patron);
        when(patronMapper.toDto(patron)).thenReturn(patronDto);

        PatronDto returnedPatronDto = patronService.addPatron(patronDto);

        Assertions.assertThat(returnedPatronDto).isNotNull();
        Assertions.assertThat(returnedPatronDto.getLastName()).isEqualTo("Doe");
    }
    @Test
    void patronService_AddPatron_ShouldReturnPatronAlreadyExistsException() {
        Patron existingPatron = Patron.builder()
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

        // Mock the repository to return an existing patron
        when(patronRepository.findByPhoneNumberAndEmail(patronDto.getPhoneNumber(), patronDto.getEmail())).thenReturn(Optional.of(existingPatron));

        // Call the method under test and assert that the exception is thrown
        Assertions.assertThatThrownBy(() -> patronService.addPatron(patronDto))
                .isInstanceOf(PatronAlreadyExistsException.class)
                .hasMessage("Patron already found with the same number or email.");
    }


@Test
void patronService_UpdatePatron_ShouldReturnPatronDto() {
    Patron originalPatron = Patron.builder()
            .id(1L)
            .membershipDate(LocalDate.parse("12/12/2024", DateTimeFormatter.ofPattern("dd/MM/yyyy")))
            .phoneNumber("0999999999")
            .email("kasem@exmaple.com")
            .firstName("Jon")
            .lastName("Doe")
            .build();

    PatronDto patronDto = PatronDto.builder()
            .phoneNumber("0999999999")
            .email("kasem@exmaple.com")
            .firstName("kasem")
            .lastName("kasem")
            .build();

    Patron updatedPatron = Patron.builder()
            .id(1L)
            .membershipDate(LocalDate.parse("12/12/2024", DateTimeFormatter.ofPattern("dd/MM/yyyy")))
            .phoneNumber("0999999999")
            .email("kasem@exmaple.com")
            .firstName("kasem")
            .lastName("kasem")
            .build();

    PatronDto updatedPatronDto = PatronDto.builder()
            .phoneNumber("0999999999")
            .email("kasem@exmaple.com")
            .firstName("kasem")
            .lastName("kasem")
            .membershipDate(LocalDate.parse("12/12/2024", DateTimeFormatter.ofPattern("dd/MM/yyyy")))
            .build();

    // Use doReturn().when() for stubbing
    when(patronRepository.findById(1L)).thenReturn(Optional.of(originalPatron));
    when(patronRepository.save(updatedPatron)).thenReturn(updatedPatron);
    when(patronMapper.toDto(updatedPatron)).thenReturn(updatedPatronDto);

    PatronDto returnedPatronDto = patronService.updatePatron(1L, patronDto);

    Assertions.assertThat(returnedPatronDto).isNotNull();
    Assertions.assertThat(returnedPatronDto.getFirstName()).isEqualTo("kasem");
    Assertions.assertThat(returnedPatronDto.getLastName()).isEqualTo("kasem");
    Assertions.assertThat(returnedPatronDto.getPhoneNumber()).isEqualTo("0999999999");
    Assertions.assertThat(returnedPatronDto.getEmail()).isEqualTo("kasem@exmaple.com");
    Assertions.assertThat(returnedPatronDto.getMembershipDate()).isEqualTo(LocalDate.parse("12/12/2024", DateTimeFormatter.ofPattern("dd/MM/yyyy")));
}
    @Test
    void patronService_UpdatePatron_ShouldReturnNoPatronFoundException() {
        // Arrange
        when(patronRepository.findById(Mockito.any(Long.class))).thenReturn(Optional.empty());

        // Act & Assert
        Assertions.assertThatThrownBy(() -> patronService.updatePatron(1L, Mockito.any(PatronDto.class)))
                .isInstanceOf(NoPatronFoundException.class)
                .hasMessageContaining("Patron with id 1 is not existed");
    }



    @Test
    void patronService_DeletePatron_ShouldReturnEmpty() {
        Long patronId = 1L;
       when(patronRepository.existsById(Mockito.any(Long.class))).thenReturn(true);

       patronService.deletePatron(patronId);
       Mockito.verify(patronRepository , Mockito.times(1)).deleteById(patronId);


    }
}