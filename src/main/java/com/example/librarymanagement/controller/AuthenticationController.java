package com.example.librarymanagement.controller;

import com.example.librarymanagement.dto.LoginResponse;
import com.example.librarymanagement.dto.LoginUserDto;
import com.example.librarymanagement.dto.RegisterUserDto;
import com.example.librarymanagement.entity.UserEntity;
import com.example.librarymanagement.service.AuthenticationService;
import com.example.librarymanagement.service.JwtService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth")
public class AuthenticationController {
private final JwtService jwtService;
private final AuthenticationService authenticationService;

    public AuthenticationController(JwtService jwtService, AuthenticationService authenticationService) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
    }
    @PostMapping("/signup")
    public ResponseEntity<UserEntity> registerUser(@RequestBody RegisterUserDto registerUserDto) {
        UserEntity registeredUserEntity = authenticationService.signUp(registerUserDto);

        return ResponseEntity.ok(registeredUserEntity);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticateUser(@RequestBody LoginUserDto loginUserDto) {
        UserEntity authenticatedUserEntity = authenticationService.authenticateUser(loginUserDto);

        String jwtToken = jwtService.generateToken(authenticatedUserEntity);

        LoginResponse loginResponse = new LoginResponse();
                loginResponse.setToken(jwtToken);

                loginResponse.setExpiresIn(jwtService.getExpirationTime());

        return ResponseEntity.ok(loginResponse);
    }

}
