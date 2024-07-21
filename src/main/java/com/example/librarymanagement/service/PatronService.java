package com.example.librarymanagement.service;

import com.example.librarymanagement.dto.PatronDto;
import com.example.librarymanagement.entity.Patron;
import com.example.librarymanagement.exception.NoPatronFoundException;
import com.example.librarymanagement.exception.PatronAlreadyExistsException;
import com.example.librarymanagement.repository.PatronRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PatronService {

    private final PatronRepository patronRepository;
    private final ModelMapper modelMapper;

    public PatronService(PatronRepository patronRepository, ModelMapper modelMapper) {
        this.patronRepository = patronRepository;
        this.modelMapper = modelMapper;
    }

    public List<PatronDto> findAllPatrons() {
        List<Patron> patrons = patronRepository.findAll();
        return patrons.stream().map(patron -> modelMapper.map(patron , PatronDto.class))
                .collect(Collectors.toList());
    }

    public PatronDto findPatronById(Long id) {
        Patron patron = patronRepository.findById(id)
                .orElseThrow(() -> new NoPatronFoundException("No patron found with id: " + id));
        return modelMapper.map(patron ,  PatronDto.class);
    }

    public PatronDto addPatron(PatronDto patronDto) {
        // Check if a patron with the same phone number or email already exists
        Optional<Patron> existingPatron = patronRepository.findByPhoneNumberAndEmail(patronDto.getPhoneNumber(), patronDto.getEmail());
        if (existingPatron.isPresent()) {
            // Throw an exception if the patron already exists
            throw new PatronAlreadyExistsException("Patron already found with the same number or email.");
        }

        // Map the PatronDto to a Patron entity
        Patron newPatron = modelMapper.map(patronDto, Patron.class);

        // Save the new patron entity to the repository
        newPatron = patronRepository.save(newPatron);

        // Map the saved Patron entity back to a PatronDto
        return modelMapper.map(newPatron, PatronDto.class);
    }


    public PatronDto updatePatron(Long id, PatronDto patronDto) {
        Optional<Patron> existingPatronOpt = patronRepository.findById(id);

        if (existingPatronOpt.isEmpty()) {
            throw new NoPatronFoundException("Can't be updated, patron with id:" + id + " not found");
        }

        Patron existingPatron = existingPatronOpt.get();
        modelMapper.map(patronDto, existingPatron);

        Patron updatedPatron = patronRepository.save(existingPatron);
        return modelMapper.map(updatedPatron , PatronDto.class);
    }

    public void deletePatron(Long id) {
        if (!patronRepository.existsById(id)) {
            throw new NoPatronFoundException("No patron found with id: " + id);
        }
        patronRepository.deleteById(id);
    }
}
