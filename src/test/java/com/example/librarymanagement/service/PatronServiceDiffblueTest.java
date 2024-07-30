package com.example.librarymanagement.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.librarymanagement.dto.PatronDto;
import com.example.librarymanagement.entity.Patron;
import com.example.librarymanagement.exception.NoPatronFoundException;
import com.example.librarymanagement.exception.PatronAlreadyExistsException;
import com.example.librarymanagement.mapper.PatronMapper;
import com.example.librarymanagement.repository.PatronRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {PatronService.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class PatronServiceDiffblueTest {
    @MockBean
    private PatronMapper patronMapper;

    @MockBean
    private PatronRepository patronRepository;

    @Autowired
    private PatronService patronService;

    /**
     * Method under test: {@link PatronService#findAllPatrons()}
     */
    @Test
    void testFindAllPatrons() {
        // Arrange
        when(patronRepository.findAll()).thenReturn(new ArrayList<>());

        // Act
        List<PatronDto> actualFindAllPatronsResult = patronService.findAllPatrons();

        // Assert
        verify(patronRepository).findAll();
        assertTrue(actualFindAllPatronsResult.isEmpty());
    }

    /**
     * Method under test: {@link PatronService#findPatronById(Long)}
     */
    @Test
    void testFindPatronById() {
        // Arrange
        Patron patron = new Patron();
        patron.setEmail("jane.doe@example.org");
        patron.setFirstName("Jane");
        patron.setId(1L);
        patron.setLastName("Doe");
        patron.setMembershipDate(LocalDate.of(1970, 1, 1));
        patron.setPhoneNumber("6625550144");
        Optional<Patron> ofResult = Optional.of(patron);
        when(patronRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        PatronDto.PatronDtoBuilder lastNameResult = PatronDto.builder()
                .email("jane.doe@example.org")
                .firstName("Jane")
                .lastName("Doe");
        LocalDate membershipDate = LocalDate.of(1970, 1, 1);
        PatronDto buildResult = lastNameResult.membershipDate(membershipDate).phoneNumber("6625550144").build();
        when(patronMapper.toDto(Mockito.any())).thenReturn(buildResult);

        // Act
        PatronDto actualFindPatronByIdResult = patronService.findPatronById(1L);

        // Assert
        verify(patronMapper).toDto(isA(Patron.class));
        verify(patronRepository).findById(eq(1L));
        LocalDate membershipDate2 = actualFindPatronByIdResult.getMembershipDate();
        assertEquals("1970-01-01", membershipDate2.toString());
        assertEquals("6625550144", actualFindPatronByIdResult.getPhoneNumber());
        assertEquals("Doe", actualFindPatronByIdResult.getLastName());
        assertEquals("Jane", actualFindPatronByIdResult.getFirstName());
        assertEquals("jane.doe@example.org", actualFindPatronByIdResult.getEmail());
        assertSame(membershipDate, membershipDate2);
    }

    /**
     * Method under test: {@link PatronService#findPatronById(Long)}
     */
    @Test
    void testFindPatronById2() {
        // Arrange
        Patron patron = new Patron();
        patron.setEmail("jane.doe@example.org");
        patron.setFirstName("Jane");
        patron.setId(1L);
        patron.setLastName("Doe");
        patron.setMembershipDate(LocalDate.of(1970, 1, 1));
        patron.setPhoneNumber("6625550144");
        Optional<Patron> ofResult = Optional.of(patron);
        when(patronRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        when(patronMapper.toDto(Mockito.any())).thenThrow(new PatronAlreadyExistsException("An error occurred"));

        // Act and Assert
        assertThrows(PatronAlreadyExistsException.class, () -> patronService.findPatronById(1L));
        verify(patronMapper).toDto(isA(Patron.class));
        verify(patronRepository).findById(eq(1L));
    }

    /**
     * Method under test: {@link PatronService#findPatronById(Long)}
     */
    @Test
    void testFindPatronById3() {
        // Arrange
        Optional<Patron> emptyResult = Optional.empty();
        when(patronRepository.findById(Mockito.<Long>any())).thenReturn(emptyResult);

        // Act and Assert
        assertThrows(NoPatronFoundException.class, () -> patronService.findPatronById(1L));
        verify(patronRepository).findById(eq(1L));
    }

    /**
     * Method under test: {@link PatronService#addPatron(PatronDto)}
     */
    @Test
    void testAddPatron() {
        // Arrange
        Patron patron = new Patron();
        patron.setEmail("jane.doe@example.org");
        patron.setFirstName("Jane");
        patron.setId(1L);
        patron.setLastName("Doe");
        patron.setMembershipDate(LocalDate.of(1970, 1, 1));
        patron.setPhoneNumber("6625550144");
        Optional<Patron> ofResult = Optional.of(patron);
        when(patronRepository.findByPhoneNumberAndEmail(Mockito.any(), Mockito.any())).thenReturn(ofResult);

        // Act and Assert
        assertThrows(PatronAlreadyExistsException.class, () -> patronService.addPatron(new PatronDto()));
        verify(patronRepository).findByPhoneNumberAndEmail(isNull(), isNull());
    }

    /**
     * Method under test: {@link PatronService#addPatron(PatronDto)}
     */
    @Test
    void testAddPatron2() {
        // Arrange
        Patron patron = new Patron();
        patron.setEmail("jane.doe@example.org");
        patron.setFirstName("Jane");
        patron.setId(1L);
        patron.setLastName("Doe");
        patron.setMembershipDate(LocalDate.of(1970, 1, 1));
        patron.setPhoneNumber("6625550144");
        when(patronRepository.save(Mockito.any())).thenReturn(patron);
        Optional<Patron> emptyResult = Optional.empty();
        when(patronRepository.findByPhoneNumberAndEmail(Mockito.any(), Mockito.any()))
                .thenReturn(emptyResult);

        Patron patron2 = new Patron();
        patron2.setEmail("jane.doe@example.org");
        patron2.setFirstName("Jane");
        patron2.setId(1L);
        patron2.setLastName("Doe");
        patron2.setMembershipDate(LocalDate.of(1970, 1, 1));
        patron2.setPhoneNumber("6625550144");
        PatronDto.PatronDtoBuilder lastNameResult = PatronDto.builder()
                .email("jane.doe@example.org")
                .firstName("Jane")
                .lastName("Doe");
        LocalDate membershipDate = LocalDate.of(1970, 1, 1);
        PatronDto buildResult = lastNameResult.membershipDate(membershipDate).phoneNumber("6625550144").build();
        when(patronMapper.toDto(Mockito.any())).thenReturn(buildResult);
        when(patronMapper.toEntity(Mockito.any())).thenReturn(patron2);

        // Act
        PatronDto actualAddPatronResult = patronService.addPatron(new PatronDto());

        // Assert
        verify(patronMapper).toDto(isA(Patron.class));
        verify(patronMapper).toEntity(isA(PatronDto.class));
        verify(patronRepository).findByPhoneNumberAndEmail(isNull(), isNull());
        verify(patronRepository).save(isA(Patron.class));
        LocalDate membershipDate2 = actualAddPatronResult.getMembershipDate();
        assertEquals("1970-01-01", membershipDate2.toString());
        assertEquals("6625550144", actualAddPatronResult.getPhoneNumber());
        assertEquals("Doe", actualAddPatronResult.getLastName());
        assertEquals("Jane", actualAddPatronResult.getFirstName());
        assertEquals("jane.doe@example.org", actualAddPatronResult.getEmail());
        assertSame(membershipDate, membershipDate2);
    }

    /**
     * Method under test: {@link PatronService#updatePatron(Long, PatronDto)}
     */
    @Test
    void testUpdatePatron() {
        // Arrange
        Patron patron = new Patron();
        patron.setEmail("jane.doe@example.org");
        patron.setFirstName("Jane");
        patron.setId(1L);
        patron.setLastName("Doe");
        patron.setMembershipDate(LocalDate.of(1970, 1, 1));
        patron.setPhoneNumber("6625550144");
        Optional<Patron> ofResult = Optional.of(patron);

        Patron patron2 = new Patron();
        patron2.setEmail("jane.doe@example.org");
        patron2.setFirstName("Jane");
        patron2.setId(1L);
        patron2.setLastName("Doe");
        patron2.setMembershipDate(LocalDate.of(1970, 1, 1));
        patron2.setPhoneNumber("6625550144");
        when(patronRepository.save(Mockito.any())).thenReturn(patron2);
        when(patronRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        PatronDto.PatronDtoBuilder lastNameResult = PatronDto.builder()
                .email("jane.doe@example.org")
                .firstName("Jane")
                .lastName("Doe");
        LocalDate membershipDate = LocalDate.of(1970, 1, 1);
        PatronDto buildResult = lastNameResult.membershipDate(membershipDate).phoneNumber("6625550144").build();
        when(patronMapper.toDto(Mockito.any())).thenReturn(buildResult);

        // Act
        PatronDto actualUpdatePatronResult = patronService.updatePatron(1L, new PatronDto());

        // Assert
        verify(patronMapper).toDto(isA(Patron.class));
        verify(patronRepository).findById(eq(1L));
        verify(patronRepository).save(isA(Patron.class));
        LocalDate membershipDate2 = actualUpdatePatronResult.getMembershipDate();
        assertEquals("1970-01-01", membershipDate2.toString());
        assertEquals("6625550144", actualUpdatePatronResult.getPhoneNumber());
        assertEquals("Doe", actualUpdatePatronResult.getLastName());
        assertEquals("Jane", actualUpdatePatronResult.getFirstName());
        assertEquals("jane.doe@example.org", actualUpdatePatronResult.getEmail());
        assertSame(membershipDate, membershipDate2);
    }

    /**
     * Method under test: {@link PatronService#updatePatron(Long, PatronDto)}
     */
    @Test
    void testUpdatePatron2() {
        // Arrange
        Patron patron = new Patron();
        patron.setEmail("jane.doe@example.org");
        patron.setFirstName("Jane");
        patron.setId(1L);
        patron.setLastName("Doe");
        patron.setMembershipDate(LocalDate.of(1970, 1, 1));
        patron.setPhoneNumber("6625550144");
        Optional<Patron> ofResult = Optional.of(patron);

        Patron patron2 = new Patron();
        patron2.setEmail("jane.doe@example.org");
        patron2.setFirstName("Jane");
        patron2.setId(1L);
        patron2.setLastName("Doe");
        patron2.setMembershipDate(LocalDate.of(1970, 1, 1));
        patron2.setPhoneNumber("6625550144");
        when(patronRepository.save(Mockito.any())).thenReturn(patron2);
        when(patronRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        when(patronMapper.toDto(Mockito.any())).thenThrow(new PatronAlreadyExistsException("An error occurred"));

        // Act and Assert
        assertThrows(PatronAlreadyExistsException.class, () -> patronService.updatePatron(1L, new PatronDto()));
        verify(patronMapper).toDto(isA(Patron.class));
        verify(patronRepository).findById(eq(1L));
        verify(patronRepository).save(isA(Patron.class));
    }

    /**
     * Method under test: {@link PatronService#updatePatron(Long, PatronDto)}
     */
    @Test
    void testUpdatePatron3() {
        // Arrange
        Optional<Patron> emptyResult = Optional.empty();
        when(patronRepository.findById(Mockito.<Long>any())).thenReturn(emptyResult);

        // Act and Assert
        assertThrows(NoPatronFoundException.class, () -> patronService.updatePatron(1L, new PatronDto()));
        verify(patronRepository).findById(eq(1L));
    }

    /**
     * Method under test: {@link PatronService#deletePatron(Long)}
     */
    @Test
    void testDeletePatron() {
        // Arrange
        doNothing().when(patronRepository).deleteById(Mockito.<Long>any());
        when(patronRepository.existsById(Mockito.<Long>any())).thenReturn(true);

        // Act
        patronService.deletePatron(1L);

        // Assert that nothing has changed
        verify(patronRepository).deleteById(eq(1L));
        verify(patronRepository).existsById(eq(1L));
    }

    /**
     * Method under test: {@link PatronService#deletePatron(Long)}
     */
    @Test
    void testDeletePatron2() {
        // Arrange
        doThrow(new PatronAlreadyExistsException("An error occurred")).when(patronRepository)
                .deleteById(Mockito.<Long>any());
        when(patronRepository.existsById(Mockito.<Long>any())).thenReturn(true);

        // Act and Assert
        assertThrows(PatronAlreadyExistsException.class, () -> patronService.deletePatron(1L));
        verify(patronRepository).deleteById(eq(1L));
        verify(patronRepository).existsById(eq(1L));
    }

    /**
     * Method under test: {@link PatronService#deletePatron(Long)}
     */
    @Test
    void testDeletePatron3() {
        // Arrange
        when(patronRepository.existsById(Mockito.<Long>any())).thenReturn(false);

        // Act and Assert
        assertThrows(NoPatronFoundException.class, () -> patronService.deletePatron(1L));
        verify(patronRepository).existsById(eq(1L));
    }
}
