package com.example.librarymanagement.controller;

import com.example.librarymanagement.dto.PatronDto;
import com.example.librarymanagement.service.PatronService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class PatronControllerTest {

    private MockMvc mockMvc;

    @Mock
    private PatronService patronService;

    @InjectMocks
    private PatronController patronController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(patronController).build();
    }

    @Test
    public void testGetAllPatrons() throws Exception {
        // Given
        PatronDto patron1 = new PatronDto("kasem", "John Doe", "john.doe@example.com", "1234567890");
        PatronDto patron2 = new PatronDto("hala", "Jane Smith", "jane.smith@example.com", "9876543210");
        List<PatronDto> patrons = Arrays.asList(patron1, patron2);

        when(patronService.findAllPatrons()).thenReturn(patrons);

        // When/Then
        mockMvc.perform(MockMvcRequestBuilders.get("/api/patrons"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()").value(patrons.size()));
    }

    @Test
    public void testGetPatronById() throws Exception {
        // Given
        Long patronId = 1L;
        PatronDto patron = new PatronDto("kasem", "John Doe", "john.doe@example.com", "1234567890");

        when(patronService.findPatronById(patronId)).thenReturn(patron);

        // When/Then
        mockMvc.perform(MockMvcRequestBuilders.get("/api/patrons/{id}", patronId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(patronId));
    }

    @Test
    public void testCreatePatron() throws Exception {
        // Given
        PatronDto patronDto = new PatronDto(null, "John Doe", "john.doe@example.com", "1234567890");
        PatronDto createdPatron = new PatronDto("kasem", "John Doe", "john.doe@example.com", "1234567890");

        when(patronService.addPatron(any(PatronDto.class))).thenReturn(createdPatron);

        // When
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/patrons")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(patronDto)))
                .andExpect(status().isCreated())
                .andReturn();

        // Then
        String responseContent = result.getResponse().getContentAsString();
        PatronDto responsePatron = new ObjectMapper().readValue(responseContent, PatronDto.class);
        assert responsePatron != null;
        assertEquals(createdPatron.getFirstName(), responsePatron.getFirstName());
        assertEquals(createdPatron.getLastName(), responsePatron.getLastName());
        assertEquals(createdPatron.getEmail(), responsePatron.getEmail());
        assertEquals(createdPatron.getPhoneNumber(), responsePatron.getPhoneNumber());
    }

    @Test
    public void testUpdatePatron() throws Exception {
        // Given
        Long patronId = 1L;
        PatronDto patronDto = new PatronDto("kasem", "John Doe", "john.doe@example.com", "1234567890");
        PatronDto updatedPatron = new PatronDto("kasem", "John Doe Jr.", "john.doe.jr@example.com", "9876543210");

        when(patronService.updatePatron(patronId, patronDto)).thenReturn(updatedPatron);

        // When
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put("/api/patrons/{id}", patronId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(patronDto)))
                .andExpect(status().isOk())
                .andReturn();

        // Then
        String responseContent = result.getResponse().getContentAsString();
        PatronDto responsePatron = new ObjectMapper().readValue(responseContent, PatronDto.class);
        assertEquals(updatedPatron.getFirstName(), responsePatron.getFirstName());
        assertEquals(updatedPatron.getLastName() , responsePatron.getLastName());
        assertEquals(updatedPatron.getEmail(), responsePatron.getEmail());
        assertEquals(updatedPatron.getPhoneNumber(), responsePatron.getPhoneNumber());
    }

    @Test
    public void testDeletePatron() throws Exception {
        // Given
        Long patronId = 1L;

        // When/Then
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/patrons/{id}", patronId))
                .andExpect(status().isNoContent());
    }
}
