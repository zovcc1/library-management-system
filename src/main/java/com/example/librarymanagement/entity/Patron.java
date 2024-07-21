package com.example.librarymanagement.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;

import java.time.LocalDate;
import java.util.Date;

@Data
@Entity
@Table(name = "PATRON_TABLE")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Patron {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "patron_seq_gen")
    @SequenceGenerator(name = "patron_seq_gen", sequenceName = "patron_sequence", allocationSize = 1)
    @JsonIgnore
    private Long id;

    @Column(name = "first_name", length = 100, nullable = false)
    private String firstName;

    @Column(name = "last_name", length = 100, nullable = false)
    private String lastName;

    @Column(name = "email", length = 150, nullable = false, unique = true , updatable = false)
    private String email;
    @NumberFormat
    @Column(name = "phone_number", length = 15)
    private String phoneNumber;

//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    @Column(name = "membership_date", nullable = false)
    @DateTimeFormat(pattern = "dd/mm/yyyy")
    private LocalDate membershipDate;
    @PrePersist
    protected void onCreate(){
        if (membershipDate == null){
            membershipDate = LocalDate.now();
        }
    }

}
