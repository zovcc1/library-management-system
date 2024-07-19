package com.example.librarymanagement.service;

import com.example.librarymanagement.dto.PatronDto;
import com.example.librarymanagement.entity.Patron;
import com.example.librarymanagement.exception.NoPatronFoundException;
import com.example.librarymanagement.exception.PatronAlreadyExistsException;
import com.example.librarymanagement.mapper.PatronMapper;
import com.example.librarymanagement.repository.PatronRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PatronService {

    private final PatronRepository patronRepository;
    private final PatronMapper patronMapper;

    @Autowired
    public PatronService(PatronRepository patronRepository, PatronMapper patronMapper) {
        this.patronRepository = patronRepository;
        this.patronMapper = patronMapper;
    }

    public List<PatronDto> findAllPatrons() {
        List<Patron> patrons = patronRepository.findAll();
        return patronMapper.map(patrons);
    }

    public PatronDto findPatronById(Long id) {
        Patron patron = patronRepository.findById(id)
                .orElseThrow(() -> new NoPatronFoundException("No patron found with id: " + id));
        return patronMapper.map(patron);
    }

    public PatronDto addPatron(PatronDto patronDto) {
        Optional<Patron> existingPatron = patronRepository.findByPhoneNumberAndEmail(patronDto.getPhoneNumber(), patronDto.getEmail());
        if (existingPatron.isPresent()) {
            throw new PatronAlreadyExistsException("Patron already found with the same number or email.");
        }
        Patron patron = patronMapper.map(patronDto);
        Patron newPatron = patronRepository.save(patron);
        return patronMapper.map(newPatron);
    }

    public PatronDto updatePatron(Long id, PatronDto patronDto) {
        Optional<Patron> existingPatronOpt = patronRepository.findById(id);

        if (existingPatronOpt.isEmpty()) {
            throw new NoPatronFoundException("Can't be updated, patron with id:" + id + " not found");
        }

        Patron existingPatron = existingPatronOpt.get();
        patronMapper.updatePatronFromDto(patronDto, existingPatron);

        Patron updatedPatron = patronRepository.save(existingPatron);
        return patronMapper.map(updatedPatron);
    }

    public void deletePatron(Long id) {
        if (!patronRepository.existsById(id)) {
            throw new NoPatronFoundException("No patron found with id: " + id);
        }
        patronRepository.deleteById(id);
    }
}
