package com.social.join.service;

import com.social.join.dtos.UserCreateRequest;
import com.social.join.dtos.UserDTO;
import com.social.join.dtos.UserUpdateRequest;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface IUserService {
    Page<UserDTO> getAllUsers(String username, String firstname, String lastname, Integer pageNumber, Integer pageSize);
    Optional<UserDTO> getUserById(int id);
    UserDTO createUser(UserCreateRequest userCreateRequest);
    Optional<UserDTO> updateUser(int id, UserUpdateRequest userUpdateRequest);
    Boolean deleteUser(int id);
}
