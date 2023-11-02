package com.social.join.service;

import com.social.join.dtos.UserCreateRequest;
import com.social.join.dtos.UserDTO;
import com.social.join.dtos.UserUpdateRequest;
import com.social.join.entities.User;
import com.social.join.mappers.IUserMapper;
import com.social.join.repositories.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
@Primary
@RequiredArgsConstructor
public class UserServiceImpl implements IUserService {
    private final IUserRepository userRepository;
    private final IUserMapper userMapper;


    @Override
    public List<UserDTO> getUsersBySearch(String username, String firstname, String lastname) {
        List<User> userList = new ArrayList<>();

        if (StringUtils.hasText(username)) {
            userList.addAll(userRepository.findAllByUsernameLikeIgnoreCase("%" + username + "%"));
        }

        if (StringUtils.hasText(firstname)) {
            userList.addAll(userRepository.findAllByFirstnameLikeIgnoreCase("%" + firstname + "%"));
        }

        if (StringUtils.hasText(lastname)) {
            userList.addAll(userRepository.findAllByLastnameLikeIgnoreCase("%" + lastname + "%"));
        }

        return userList.stream()
                .map(userMapper::userToUserDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(userMapper::userToUserDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<UserDTO> getUserById(int id) {
        return Optional.ofNullable(userMapper.userToUserDTO(userRepository.findById(id).orElse(null)));
    }

    @Override
    public UserDTO createUser(UserCreateRequest userCreateRequest) {
        return userMapper.userToUserDTO(
                userRepository.save( userMapper.userCreateRequestToUser(userCreateRequest) )
        );
    }

    @Override
    public Optional<UserDTO> updateUser(int id, UserUpdateRequest userUpdateRequest) {
        AtomicReference<Optional<UserDTO>> atomicReference = new AtomicReference<>();

        userRepository.findById(id).ifPresentOrElse(foundUser -> {
            foundUser.setFirstname(userUpdateRequest.getFirstname());
            foundUser.setUsername(userUpdateRequest.getUsername());
            foundUser.setLastname(userUpdateRequest.getLastname());
            foundUser.setPassword(userUpdateRequest.getPassword());
            foundUser.setVersion(userUpdateRequest.getVersion());
            foundUser.setEmail(userUpdateRequest.getEmail());
            atomicReference.set(Optional.of(userMapper.userToUserDTO(userRepository.save(foundUser))));
        }, () -> {
            atomicReference.set(Optional.empty());
        });

        return atomicReference.get();
    }

    @Override
    public Boolean deleteUser(int id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
