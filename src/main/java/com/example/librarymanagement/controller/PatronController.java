    package com.example.librarymanagement.controller;

    import com.example.librarymanagement.dto.PatronDto;
    import com.example.librarymanagement.service.PatronService;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.http.HttpStatus;
    import org.springframework.http.ResponseEntity;
    import org.springframework.web.bind.annotation.*;

    import java.util.List;

    @RestController
    @RequestMapping("/api/patrons")
    public class PatronController {

        private final PatronService patronService;

        @Autowired
        public PatronController(PatronService patronService) {
            this.patronService = patronService;
        }

        @GetMapping
        public ResponseEntity<List<PatronDto>> getAllPatrons() {
            List<PatronDto> patrons = patronService.findAllPatrons();
            return new ResponseEntity<>(patrons, HttpStatus.OK);
        }

        @GetMapping("/{id}")
        public ResponseEntity<PatronDto> getPatronById(@PathVariable Long id) {
            PatronDto patron = patronService.findPatronById(id);
            return new ResponseEntity<>(patron, HttpStatus.OK);
        }

        @PostMapping
        public ResponseEntity<PatronDto> createPatron(@RequestBody PatronDto patronDto) {
            PatronDto newPatron = patronService.addPatron(patronDto);
            return new ResponseEntity<>(newPatron, HttpStatus.CREATED);
        }

        @PutMapping("/{id}")
        public ResponseEntity<PatronDto> updatePatron(@PathVariable Long id, @RequestBody PatronDto patronDto) {
            PatronDto updatedPatron = patronService.updatePatron(id, patronDto);
            return new ResponseEntity<>(updatedPatron, HttpStatus.OK);
        }

        @DeleteMapping("/{id}")
        public ResponseEntity<Void> deletePatron(@PathVariable Long id) {
            patronService.deletePatron(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }
