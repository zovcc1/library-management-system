package com.example.librarymanagement.controller;

import com.example.librarymanagement.repository.UserEntityRepository;
import com.example.librarymanagement.service.UserEntityService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ContextConfiguration(classes = {UserEntityController.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class UserEntityControllerDiffblueTest {
    @Autowired
    private UserEntityController userEntityController;

    @MockBean
    private UserEntityService userEntityService;

    /**
     * Method under test: {@link UserEntityController#authenticatedUser()}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testAuthenticatedUser() {
        // TODO: Diffblue Cover was only able to create a partial test for this method:
        //   Reason: No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   jakarta.servlet.ServletException: Request processing failed: java.lang.NullPointerException: Cannot invoke "org.springframework.security.core.Authentication.getPrincipal()" because "authentication" is null
        //       at jakarta.servlet.http.HttpServlet.service(HttpServlet.java:564)
        //       at jakarta.servlet.http.HttpServlet.service(HttpServlet.java:658)
        //   java.lang.NullPointerException: Cannot invoke "org.springframework.security.core.Authentication.getPrincipal()" because "authentication" is null
        //       at com.example.librarymanagement.controller.UserEntityController.authenticatedUser(UserEntityController.java:27)
        //       at jakarta.servlet.http.HttpServlet.service(HttpServlet.java:564)
        //       at jakarta.servlet.http.HttpServlet.service(HttpServlet.java:658)
        //   See https://diff.blue/R013 to resolve this issue.

        // Arrange and Act
        (new UserEntityController(new UserEntityService(mock(UserEntityRepository.class)))).authenticatedUser();
    }

    /**
     * Method under test: {@link UserEntityController#allUsers()}
     */
    @Test
    void testAllUsers() throws Exception {
        // Arrange
        when(userEntityService.allUsers()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/users/");

        // Act and Assert
        MockMvcBuilders.standaloneSetup(userEntityController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }
}
