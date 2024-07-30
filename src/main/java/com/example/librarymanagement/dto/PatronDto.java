package com.example.librarymanagement.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PatronDto {
    private String firstName;

    private String lastName;

    private String email;

    private String phoneNumber;
    @DateTimeFormat(pattern = "dd/mm/yyyy")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDate membershipDate;


}
