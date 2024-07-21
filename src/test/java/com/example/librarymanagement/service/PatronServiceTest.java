package com.example.librarymanagement.service;

import com.example.librarymanagement.entity.Patron;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;

@DataJpaTest
class PatronServiceTest {
    private PatronService patronService;

    @BeforeEach
    void setUp() {
        Patron patron = new Patron();
        patron.setFirstName("mohammed khair");
        patron.setLastName("alchikh ali");
        patron.setPhoneNumber("0991951404");
        patron.setEmail("qassemmohammedli@gmail.com");
        patron.setMembershipDate(LocalDate.of(2020, 1, 1));


    }

    @AfterEach
    void tearDown() {

    }

    @Test
    void findAllPatrons() {
    }

    @Test
    void findPatronById() {
    }

    @Test
    void addPatron() {
    }

    @Test
    void updatePatron() {
    }

    @Test
    void deletePatron() {
    }
}