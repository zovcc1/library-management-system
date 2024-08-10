package com.example.librarymanagement.service;

import com.example.librarymanagement.dto.PatronDto;
import com.example.librarymanagement.entity.Patron;
import com.example.librarymanagement.exception.NoPatronFoundException;
import com.example.librarymanagement.exception.PatronAlreadyExistsException;
import com.example.librarymanagement.mapper.PatronMapper;
import com.example.librarymanagement.repository.PatronRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PatronService {

    private final PatronRepository patronRepository;
    private final PatronMapper patronMapper;

    public PatronService(PatronRepository patronRepository, PatronMapper patronMapper) {
        this.patronRepository = patronRepository;
        this.patronMapper = patronMapper;
    }

    public List<PatronDto> findAllPatrons() {
        List<Patron> patrons = patronRepository.findAll();
        return patrons.stream().map(patronMapper::toDto)
                .collect(Collectors.toList());
    }

    public PatronDto findPatronById(Long id) {
        Patron patron = patronRepository.findById(id)
                .orElseThrow(() -> new NoPatronFoundException("No patron found with id: " + id));
        return patronMapper.toDto(patron);
    }

    public PatronDto addPatron(PatronDto patronDto) {
        Optional<Patron> existingPatron = patronRepository.findByPhoneNumberAndEmail(patronDto.getPhoneNumber(), patronDto.getEmail());
        if (existingPatron.isPresent()) {
            throw new PatronAlreadyExistsException("Patron already found with the same number or email.");
        }

        Patron newPatron = patronMapper.toEntity(patronDto);

        patronRepository.save(newPatron);

        return patronMapper.toDto(newPatron);
    }


    public PatronDto updatePatron(Long id, PatronDto patronDto) {
        Optional<Patron> existingPatronOpt = patronRepository.findById(id);

        if (existingPatronOpt.isEmpty()) {
            throw new NoPatronFoundException("Can't be updated, patron with id:" + id + " not found");
        }

        Patron existingPatron = existingPatronOpt.get();
        patronMapper.toEntity(patronDto);

        Patron updatedPatron = patronRepository.save(existingPatron);
        return patronMapper.toDto(updatedPatron);
    }

    public void deletePatron(Long id) {
        if (!patronRepository.existsById(id)) {
            throw new NoPatronFoundException("No patron found with id: " + id);
        }
        patronRepository.deleteById(id);
    }
}
