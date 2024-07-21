package com.example.librarymanagement.repository;

import com.example.librarymanagement.entity.Patron;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;


@DataJpaTest
class PatronRepositoryTest {
    @Autowired
    private PatronRepository patronRepository;


    @BeforeEach
    void setUp() {
        Patron patron1 = Patron.builder()
                .firstName("Jon")
                .lastName("Doe")
                .email("JonDoe@gmail.com")
                .phoneNumber("0999999999")
                .build();

        patronRepository.save(patron1);
    }

    @AfterEach
    void tearDown() {
        patronRepository.deleteAll();
    }

    @Test
    void patronRepository_findById_shouldReturnPatronById() {

    }


    @Test
    void patronRepository_findByPhoneNumberAndEmail_thenShouldReturnEmailAndPhoneNumber() {
        // Given
        Patron patron = Patron.builder()
                .firstName("John")
                .lastName("Doe")
                .phoneNumber("0991951404")
                .email("john.doe@example.com")
                .membershipDate(LocalDate.now())
                .build();

        // Persist the initial entity
        patronRepository.save(patron);

        // When
        Optional<Patron> foundPatron = patronRepository.findByPhoneNumberAndEmail("0991951404", "john.doe@example.com");

        // Then
        Assertions.assertThat(foundPatron).isPresent();
        Assertions.assertThat(foundPatron.get().getPhoneNumber()).isEqualTo("0991951404");
        Assertions.assertThat(foundPatron.get().getEmail()).isEqualTo("john.doe@example.com");
    }

    @Test
    void patronRepository_updatePatron_returnNotNull() {
        //given
        Patron patron = Patron.builder()
                .firstName("k")
                .lastName("k")
                .phoneNumber("0991951404")
                .email("kasem@kasem.com")
                .membershipDate(LocalDate.parse("12/12/2010", DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                .build();

        patronRepository.save(patron);

        Optional<Patron> patron1 = patronRepository.findById(patron.getId());
        Patron patronSave = patron1.get();
        patronSave.setLastName("kasem");
        patronSave.setLastName("kasem");
        Patron updatedPatron = patronRepository.save(patronSave);
        //assert
        Assertions.assertThat(updatedPatron.getLastName()).isNotNull();


    }

    @Test
    void patronRepository_PatronDelete_ThenShouldReturnIsEmpty() {
        //given
        Patron patron = Patron.builder()
                .firstName("k")
                .lastName("k")
                .phoneNumber("0991951404")
                .email("kasem@kasem.com")
                .membershipDate(null)
                .build();
        patronRepository.save(patron);
        patronRepository.deleteById(patron.getId());
        Optional<Patron> returnPatron = patronRepository.findById(patron.getId());
        Assertions.assertThat(returnPatron).isEmpty();
    }

}