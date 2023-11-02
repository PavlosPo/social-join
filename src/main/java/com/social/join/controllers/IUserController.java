package com.social.join.controllers;

import com.social.join.dtos.UserCreateRequest;
import com.social.join.dtos.UserDTO;
import com.social.join.dtos.UserUpdateRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public interface IUserController {

    @GetMapping("/users")
    ResponseEntity<List<UserDTO>> getAllUsers();

    @GetMapping("/users/{id}")
    ResponseEntity<UserDTO> getUserById(@PathVariable Integer id);

    @PostMapping("/users")
    ResponseEntity<UserDTO> createUser(@RequestBody UserCreateRequest userCreateRequest);

    @PutMapping("/users/{id}")
    ResponseEntity<UserDTO> updateUser(@PathVariable Integer id, @RequestBody UserUpdateRequest userUpdateRequest);

    @DeleteMapping("/users/{id}")
    ResponseEntity<Boolean> deleteUser(@PathVariable Integer id);


}
