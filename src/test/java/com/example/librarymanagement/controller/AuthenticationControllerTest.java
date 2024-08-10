package com.example.librarymanagement.controller;

import com.example.librarymanagement.dto.RegisterUserDto;
import com.example.librarymanagement.entity.UserEntity;
import com.example.librarymanagement.service.AuthenticationService;
import com.example.librarymanagement.service.JwtService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthenticationControllerTest {

    @Mock
    private JwtService jwtService;

    @Mock
    private AuthenticationService authenticationService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AuthenticationController authenticationController;

    private MockMvc mockMvc;
    private RegisterUserDto registerUserDto;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(authenticationController).build();

        registerUserDto = new RegisterUserDto();
        registerUserDto.setEmail("JonDoe@example.com");
        registerUserDto.setPassword("helloWorld");
        registerUserDto.setFullName("Jon Doe");

        when(passwordEncoder.encode("helloWorld")).thenReturn("encodedPassword");
        registerUserDto.setPassword(passwordEncoder.encode(registerUserDto.getPassword()));
    }

    @Test
    void authenticationController_RegisterUser_ShouldReturnOkay() throws Exception {
        // Mock the service behavior
        when(authenticationService.signUp(Mockito.any(RegisterUserDto.class))).thenReturn(new UserEntity());

        // Perform the request and assert the response
        mockMvc.perform(MockMvcRequestBuilders.post("/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(registerUserDto)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void authenticationController_authenticateUser_ShouldReturnOkay() throws Exception {
        // Mock the service behavior
        when(authenticationService.authenticateUser(Mockito.any())).thenReturn(new UserEntity());

        // Perform the request and assert the response
        mockMvc.perform(MockMvcRequestBuilders.post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\":\"JonDoe@example.com\",\"password\":\"helloWorld\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
