package com.example.librarymanagement.controller;

import com.example.librarymanagement.entity.UserEntity;
import com.example.librarymanagement.service.UserEntityService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/users")
@RestController
public class UserEntityController {
    private final UserEntityService userEntityService;

    public UserEntityController(UserEntityService userEntityService) {
        this.userEntityService = userEntityService;
    }

    @GetMapping("/me")
    public ResponseEntity<UserEntity> authenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        UserEntity currentUserEntity = (UserEntity) authentication.getPrincipal();

        return ResponseEntity.ok(currentUserEntity);
    }

    @GetMapping("/")
    public ResponseEntity<List<UserEntity>> allUsers() {
        List <UserEntity> users = userEntityService.allUsers();

        return ResponseEntity.ok(users);
    }
}