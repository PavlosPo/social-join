package com.social.join.controllers;

import com.social.join.dtos.UserCreateRequest;
import com.social.join.dtos.UserDTO;
import com.social.join.dtos.UserUpdateRequest;
import org.springframework.http.ResponseEntity;

import java.util.List;

public class UserControllerImpl implements IUserController{
    @Override
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        return null;
    }

    @Override
    public ResponseEntity<UserDTO> getUserById(Integer id) {
        return null;
    }

    @Override
    public ResponseEntity<UserDTO> createUser(UserCreateRequest userCreateRequest) {
        return null;
    }

    @Override
    public ResponseEntity<UserDTO> updateUser(Integer id, UserUpdateRequest userUpdateRequest) {
        return null;
    }

    @Override
    public ResponseEntity<Boolean> deleteUser(Integer id) {
        return null;
    }
}
