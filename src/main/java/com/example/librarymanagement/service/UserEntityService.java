package com.example.librarymanagement.service;

import com.example.librarymanagement.entity.UserEntity;
import com.example.librarymanagement.repository.UserEntityRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserEntityService {
    private final UserEntityRepository userEntityRepository;

    public UserEntityService(UserEntityRepository userEntityRepository) {
        this.userEntityRepository = userEntityRepository;
    }

    public List<UserEntity> allUsers() {

        return new ArrayList<>(userEntityRepository.findAll());
    }
}
