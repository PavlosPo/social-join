package com.social.join.service;

import com.social.join.dtos.UserCreateRequest;
import com.social.join.dtos.UserDTO;
import com.social.join.dtos.UserUpdateRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface IUserService {
    List<UserDTO> getUsersBySearch(String username, String firstname, String lastname);  // optional choices
    List<UserDTO> getAllUsers();
    Optional<UserDTO> getUserById(int id);
    UserDTO createUser(UserCreateRequest userCreateRequest);
    Optional<UserDTO> updateUser(int id, UserUpdateRequest userUpdateRequest);
    Boolean deleteUser(int id);
}
