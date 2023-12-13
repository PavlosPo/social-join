package com.social.join.controllers;

import com.social.join.dtos.UserCreateRequest;
import com.social.join.dtos.UserDTO;
import com.social.join.dtos.UserUpdateRequest;
import com.social.join.exceptions.AppException;
import com.social.join.service.IUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/users")
public class UserControllerImpl implements IUserController{

    private final IUserService userService;

    @GetMapping
    public Page<UserDTO> listUsers(
            @RequestParam(value = "username", required = false) String username,
            @RequestParam(value = "firstname", required = false) String firstname,
            @RequestParam(value = "lastname", required = false) String lastname,
            @RequestParam(value = "pageNumber", required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        return userService.getAllUsers(username, firstname, lastname, pageNumber, pageSize);
    }


    @GetMapping("/{userId}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable("userId") Integer userId) {
        log.debug("Get User by Id - in controller");
        UserDTO userDTO = userService.getUserById(userId)
                .orElseThrow(() -> new NotFoundException("The user does not exist"));
        return ResponseEntity.ok(userDTO);
    }


    @PostMapping
    public ResponseEntity<UserDTO> createUser(@Validated @RequestBody UserCreateRequest userCreateRequest) {
        UserDTO savedUser = userService.createUser(userCreateRequest);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/users/" + savedUser.getId());
        return ResponseEntity.status(HttpStatus.CREATED).headers(headers).body(savedUser);
    }


    @PatchMapping("/{userId}")
    public ResponseEntity<UserDTO> updateUser(
            @PathVariable("userId") Integer id,
            @RequestBody UserUpdateRequest userUpdateRequest) {

        if (userUpdateRequest.getId() == null){
            throw new AppException("The user id to update does not exist", HttpStatus.BAD_REQUEST);
        }
        Optional<UserDTO> updatedUser = userService.updateUser(id, userUpdateRequest);
        if (updatedUser.isEmpty()) {
            throw new AppException("The user to update does not exist", HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.status(HttpStatus.OK).body(updatedUser.get());
    }


    @DeleteMapping("/{userId}")
    public ResponseEntity<Boolean> deleteUser(@PathVariable("userId") Integer id) {
        if (!userService.deleteUser(id)) {
            throw new AppException("The user id to delete does not exists", HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
