package com.example.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.project.entity.UserEntity;

public interface IUserRepository extends JpaRepository<UserEntity, Long> {
    public UserEntity findByEmail(String email);
}
