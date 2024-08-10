package com.example.librarymanagement.controller;

import com.example.librarymanagement.entity.UserEntity;
import com.example.librarymanagement.service.UserEntityService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.when;
@ExtendWith(MockitoExtension.class)
class UserEntityControllerTest {

    @Mock
    private UserEntityService userEntityService;

    @Mock
    private Authentication authentication;

    @Mock
    private SecurityContext securityContext;

    @InjectMocks
    private UserEntityController userEntityController;
    @Mock
    private MockMvc mockMvc;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        SecurityContextHolder.setContext(securityContext);
        mockMvc = MockMvcBuilders.standaloneSetup(userEntityController).build();

    }

    @Test
    void UserEntityController_authenticatedUser_ShouldReturnUserEntity() throws Exception {
        // Arrange
        UserEntity mockUser = new UserEntity();
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(mockUser);

        // Act
        ResponseEntity<UserEntity> response = userEntityController.authenticatedUser();

        // Assert
        // Asserts that the status code is 200 OK

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/users/me")
                .contentType(MediaType.APPLICATION_JSON);
        mockMvc
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));

    }

    @Test
    void UserEntityController_allUsers_ShouldReturnListOfUserEntity() throws Exception {
        // You can remove the stubbing of userEntityService.allUsers() as it is unnecessary

        // Act & Assert
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/users/")
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

}
