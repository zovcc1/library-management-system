package com.example.librarymanagement.repository;

import com.example.librarymanagement.entity.Patron;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PatronRepository extends JpaRepository<Patron, Long> {
    Optional<Patron> findById(Long id);

    Optional<Patron> findByPhoneNumberAndEmail(String phoneNumber, String email);

}
