package com.social.join.controllers;

import com.social.join.dtos.UserCreateRequest;
import com.social.join.dtos.UserDTO;
import com.social.join.dtos.UserUpdateRequest;
import com.social.join.service.IUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@Slf4j
public class UserControllerImpl implements IUserController{

    public static final String USER_PATH = "/users";
    public static final String USER_PATH_ID = USER_PATH + "{userId}";
    private final IUserService userService;

    @Override
    public Page<UserDTO> getAllUsers(
            @RequestParam(value = "username", required = false) String username,
            @RequestParam(value = "firstname", required = false) String firstname,
            @RequestParam(value = "lastname", required = false) String lastname,
            @RequestParam(value = "pageNumber", required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        return userService.getAllUsers(username, firstname, lastname, pageNumber, pageSize);
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
