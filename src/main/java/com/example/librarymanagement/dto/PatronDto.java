package com.example.librarymanagement.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PatronDto {
    private String firstName;

    private String lastName;

    private String email;

    private String phoneNumber;
    @DateTimeFormat(pattern = "dd/mm/yyyy")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDate membershipDate;


}
