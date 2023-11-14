package com.social.join.controllers;

import com.social.join.dtos.UserCreateRequest;
import com.social.join.dtos.UserDTO;
import com.social.join.dtos.UserUpdateRequest;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


public interface IUserController {

    Page<UserDTO> listUsers(@RequestParam(value = "username", required = false) String username,
                            @RequestParam(value = "firstname", required = false) String firstname,
                            @RequestParam(value = "lastname", required = false) String lastname,
                            @RequestParam(value = "pageNumber", required = false) Integer pageNumber,
                            @RequestParam(value = "pageSize", required = false) Integer pageSize);

    ResponseEntity<UserDTO> getUserById(@PathVariable Integer userId);

    ResponseEntity<UserDTO> createUser(@RequestBody UserCreateRequest userCreateRequest);

    ResponseEntity<UserDTO> updateUser(@PathVariable Integer id, @RequestBody UserUpdateRequest userUpdateRequest);

    ResponseEntity<Boolean> deleteUser(@PathVariable Integer id);


}
