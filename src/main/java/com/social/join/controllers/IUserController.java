package com.social.join.controllers;

import com.social.join.dtos.UserCreateRequest;
import com.social.join.dtos.UserDTO;
import com.social.join.dtos.UserUpdateRequest;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public interface IUserController {

    @GetMapping("/users")
    Page<UserDTO> getAllUsers(@RequestParam(value = "username", required = false) String username,
                                              @RequestParam(value = "firstname", required = false) String firstname,
                                              @RequestParam(value = "lastname", required = false) String lastname,
                                              @RequestParam(value = "pageNumber", required = false) Integer pageNumber,
                                              @RequestParam(value = "pageSize", required = false) Integer pageSize);

    @GetMapping("/users/{id}")
    ResponseEntity<UserDTO> getUserById(@PathVariable Integer id);

    @PostMapping("/users")
    ResponseEntity<UserDTO> createUser(@RequestBody UserCreateRequest userCreateRequest);

    @PutMapping("/users/{id}")
    ResponseEntity<UserDTO> updateUser(@PathVariable Integer id, @RequestBody UserUpdateRequest userUpdateRequest);

    @DeleteMapping("/users/{id}")
    ResponseEntity<Boolean> deleteUser(@PathVariable Integer id);


}
