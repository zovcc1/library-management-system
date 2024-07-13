package com.example.librarymanagement.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PatronDto {
    private String firstName;

    private String lastName;

    private String email;

    private String phoneNumber;


}
