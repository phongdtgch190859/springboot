package com.example.project.service;

import com.example.project.entity.UserEntity;
import com.example.project.exception.UserException;

public interface IUserService {
    public UserEntity findUserById(Long userId) throws UserException;

    public UserEntity findUserProfileByJwt(String jwt) throws UserException;

}
