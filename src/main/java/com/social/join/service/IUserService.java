package com.social.join.service;

import com.social.join.dtos.UserDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface IUserService {
    List<UserDTO> getUsersBySearch(String username, String firstname, String lastname);  // optional choices
    List<UserDTO> getAllUsers();
    Optional<UserDTO> getUserById(int id);
    UserDTO saveNewUser(UserDTO userDTO);
    Optional<UserDTO> updateUserById(int id, UserDTO userDTO);
    Boolean deleteById(int id);
}
