package com.example.librarymanagement.repository;

import com.example.librarymanagement.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public  interface UserEntityRepository extends JpaRepository<UserEntity, Long> {

   Optional<UserEntity> findByEmail(String email);
}
