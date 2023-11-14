package com.social.join.service;

import com.social.join.controllers.NotFoundException;
import com.social.join.dtos.UserCreateRequest;
import com.social.join.dtos.UserDTO;
import com.social.join.dtos.UserUpdateRequest;
import com.social.join.entities.User;
import com.social.join.mappers.IUserMapper;
import com.social.join.repositories.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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

    private static final int DEFAULT_PAGE = 0;
    private static final int DEFAULT_PAGE_SIZE = 25;

    @Override
    public Page<UserDTO> getAllUsers(String username, String firstname, String lastname, Integer pageNumber, Integer pageSize) {
        PageRequest pageRequest = buildPageRequest(pageNumber, pageSize);

        Page<User> userPage;
        if (StringUtils.hasText(username) && firstname == null && lastname == null) {
            userPage = listUsersByUsername(username, pageRequest);
        } else if (username == null && StringUtils.hasText(firstname) && lastname == null) {
            userPage = listUsersByFirstname(firstname, pageRequest);
        } else if (username == null && firstname == null && StringUtils.hasText(lastname)) {
            userPage = listUsersByFirstname(lastname, pageRequest);
        } else if (StringUtils.hasText(username) && StringUtils.hasText(firstname)) {
            userPage = listUsersByUsernameAndFirstname(username, firstname, pageRequest);
        } else if (StringUtils.hasText(username) && StringUtils.hasText(lastname)) {
            userPage = listUsersByUsernameAndLastname(username, lastname, pageRequest);
        } else {
            userPage = userRepository.findAll(pageRequest);
        }

        return userPage.map(userMapper::userToUserDTO);
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
            foundUser.setEmail(userUpdateRequest.getEmail());
            atomicReference.set(Optional.of(userMapper.userToUserDTO(userRepository.save(foundUser))));
        }, () -> {
            atomicReference.set(Optional.empty());
        });

        return atomicReference.get();
    }

//    @Override
//    public Optional<UserDTO> updateUser(int id, UserUpdateRequest userUpdateRequest) {
//        User userToUpdate = userRepository.findById(id)
//                .orElseThrow(() -> new NotFoundException("User not found"));
//
//        // Check and update fields
//        if (userUpdateRequest.getFirstname() != null) {
//            userToUpdate.setFirstname(userUpdateRequest.getFirstname());
//        }
//        if (userUpdateRequest.getUsername() != null) {
//            userToUpdate.setUsername(userUpdateRequest.getUsername());
//        }
//        if (userUpdateRequest.getLastname() != null) {
//            userToUpdate.setLastname(userUpdateRequest.getLastname());
//        }
//        if (userUpdateRequest.getPassword() != null) {
//            userToUpdate.setPassword(userUpdateRequest.getPassword());
//        }
//        if (userUpdateRequest.getEmail() != null) {
//            userToUpdate.setEmail(userUpdateRequest.getEmail());
//        }
//
//        // Save the entity
//        User updatedUser = userRepository.save(userToUpdate);
//
//        return Optional.of(userMapper.userToUserDTO(updatedUser));
//    }

    @Override
    public Boolean deleteUser(int id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public PageRequest buildPageRequest(Integer pageNumber, Integer pageSize){  // Defence Coding
        int queryPageNumber;
        int queryPageSize;

        if  (pageNumber != null && pageNumber > 0) {
            queryPageNumber = pageNumber - 1;
        } else {
            queryPageNumber = DEFAULT_PAGE;
        }

        if (pageSize == null) {
            queryPageSize = DEFAULT_PAGE_SIZE;
        } else {
            if (pageSize > 1000) {
                queryPageSize = 1000;
            } else {
                queryPageSize = pageSize;
            }

        }

        Sort sort = Sort.by(Sort.Order.asc("username"));

        return PageRequest.of(queryPageNumber, queryPageSize, sort);
    }

    private Page<User> listUsersByUsername(String username, PageRequest pageRequest){
        return userRepository.findAllByFirstnameLikeIgnoreCase("%" + username + "%", pageRequest);
    }

    private Page<User> listUsersByFirstname(String firstname, PageRequest pageRequest){
        return userRepository.findAllByFirstnameLikeIgnoreCase("%" + firstname + "%", pageRequest);
    }

    private Page<User> listUsersByLastname(String lastname, PageRequest pageRequest){
        return userRepository.findAllByLastnameLikeIgnoreCase("%" + lastname + "%", pageRequest);
    }

    private Page<User> listUsersByUsernameAndFirstname(String username ,String firstname, PageRequest pageRequest){
        return userRepository.findAllByUsernameLikeIgnoreCaseAndAndFirstnameLikeIgnoreCase(
                "%" + username + "%",
                "%" + firstname + "%",
                pageRequest);
    }

    private Page<User> listUsersByUsernameAndLastname(String username ,String lastname, PageRequest pageRequest){
        return userRepository.findAllByUsernameLikeIgnoreCaseAndAndFirstnameLikeIgnoreCase(
                "%" + username + "%",
                "%" + lastname + "%",
                pageRequest);
    }


}
