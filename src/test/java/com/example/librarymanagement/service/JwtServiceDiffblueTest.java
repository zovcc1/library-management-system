package com.example.librarymanagement.service;

import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Map;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ContextConfiguration(classes = {JwtService.class})
@ExtendWith(SpringExtension.class)
class JwtServiceDiffblueTest {
    @Autowired
    private JwtService jwtService;

    /**
     * Method under test: {@link JwtService#extractUsername(String)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testExtractUsername() {
        // TODO: Diffblue Cover was only able to create a partial test for this method:
        //   Reason: Missing Spring properties.
        //   Failed to create Spring context due to unresolvable @Value
        //   properties: private long com.example.librarymanagement.service.JwtService.jwtExpiration
        //   Please check that at least one of the property files is provided
        //   and contains required variables:
        //   - application-test.properties (doesn't contain the required variables)
        //   See https://diff.blue/R033 to resolve this issue.

        // Arrange
        // TODO: Populate arranged inputs
        String token = "";

        // Act
        String actualExtractUsernameResult = this.jwtService.extractUsername(token);

        // Assert
        // TODO: Add assertions on result
    }

    /**
     * Method under test: {@link JwtService#extractClaim(String, Function)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testExtractClaim() {
        // TODO: Diffblue Cover was only able to create a partial test for this method:
        //   Reason: Missing Spring properties.
        //   Failed to create Spring context due to unresolvable @Value
        //   properties: private long com.example.librarymanagement.service.JwtService.jwtExpiration
        //   Please check that at least one of the property files is provided
        //   and contains required variables:
        //   - application-test.properties (doesn't contain the required variables)
        //   See https://diff.blue/R033 to resolve this issue.

        // Arrange
        // TODO: Populate arranged inputs
        String token = "";
        Function<Claims, Object> claimsResolver = null;

        // Act
        Object actualExtractClaimResult = this.jwtService.extractClaim(token, claimsResolver);

        // Assert
        // TODO: Add assertions on result
    }

    /**
     * Method under test: {@link JwtService#generateToken(Map, UserDetails)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testGenerateToken() {
        // TODO: Diffblue Cover was only able to create a partial test for this method:
        //   Reason: Missing Spring properties.
        //   Failed to create Spring context due to unresolvable @Value
        //   properties: private long com.example.librarymanagement.service.JwtService.jwtExpiration
        //   Please check that at least one of the property files is provided
        //   and contains required variables:
        //   - application-test.properties (doesn't contain the required variables)
        //   See https://diff.blue/R033 to resolve this issue.

        // Arrange
        // TODO: Populate arranged inputs
        Map<String, Object> extraClaims = null;
        UserDetails userDetails = null;

        // Act
        String actualGenerateTokenResult = this.jwtService.generateToken(extraClaims, userDetails);

        // Assert
        // TODO: Add assertions on result
    }

    /**
     * Method under test: {@link JwtService#generateToken(UserDetails)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testGenerateToken2() {
        // TODO: Diffblue Cover was only able to create a partial test for this method:
        //   Reason: Missing Spring properties.
        //   Failed to create Spring context due to unresolvable @Value
        //   properties: private long com.example.librarymanagement.service.JwtService.jwtExpiration
        //   Please check that at least one of the property files is provided
        //   and contains required variables:
        //   - application-test.properties (doesn't contain the required variables)
        //   See https://diff.blue/R033 to resolve this issue.

        // Arrange
        // TODO: Populate arranged inputs
        UserDetails userDetails = null;

        // Act
        String actualGenerateTokenResult = this.jwtService.generateToken(userDetails);

        // Assert
        // TODO: Add assertions on result
    }

    /**
     * Method under test: {@link JwtService#getExpirationTime()}
     */
    @Test
    void testGetExpirationTime() {
        // Arrange, Act and Assert
        assertEquals(0L, (new JwtService()).getExpirationTime());
    }

    /**
     * Method under test: {@link JwtService#isTokenValid(String, UserDetails)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testIsTokenValid() {
        // TODO: Diffblue Cover was only able to create a partial test for this method:
        //   Reason: Missing Spring properties.
        //   Failed to create Spring context due to unresolvable @Value
        //   properties: private long com.example.librarymanagement.service.JwtService.jwtExpiration
        //   Please check that at least one of the property files is provided
        //   and contains required variables:
        //   - application-test.properties (doesn't contain the required variables)
        //   See https://diff.blue/R033 to resolve this issue.

        // Arrange
        // TODO: Populate arranged inputs
        String token = "";
        UserDetails userDetails = null;

        // Act
        boolean actualIsTokenValidResult = this.jwtService.isTokenValid(token, userDetails);

        // Assert
        // TODO: Add assertions on result
    }
}
