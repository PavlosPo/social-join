package com.social.join.service;

import com.social.join.config.PasswordConfig;
import com.social.join.dtos.*;
import com.social.join.entities.User;
import com.social.join.exceptions.AppException;
import com.social.join.mappers.IUserMapper;
import com.social.join.repositories.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.nio.CharBuffer;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@Service
@Primary
@RequiredArgsConstructor
public class UserServiceImpl implements IUserService {
    private final IUserRepository userRepository;
    private final IUserMapper userMapper;
    private final PasswordEncoder passwordEncoder;


    private static final int DEFAULT_PAGE = 0;
    private static final int DEFAULT_PAGE_SIZE = 25;

    // LOGIN STUFF
    public UserDTO login(CredentialsDTO credentialsDTO) {
        System.out.println(credentialsDTO.toString());
        User user = userRepository.findByUsername(credentialsDTO.username())
                .orElseThrow(() -> new AppException("Unknown user", HttpStatus.NOT_FOUND));

        if (passwordEncoder.matches(CharBuffer.wrap(credentialsDTO.password()), user.getPassword())) {
            return userMapper.userToUserDTO(user);
        }
        throw new AppException("Invalid password", HttpStatus.BAD_REQUEST);
    }

    public UserDTO register(SignUpDTO userDto) {
        Optional<User> optionalUser = userRepository.findByUsername(userDto.username());

        if (optionalUser.isPresent()) {
            throw new AppException("Login already exists", HttpStatus.BAD_REQUEST);
        }


        User user = userMapper.signUpToUser(userDto);
        user.setPassword(passwordEncoder.encode(CharBuffer.wrap(userDto.password())));

        User savedUser = userRepository.save(user);

        return userMapper.userToUserDTO(savedUser);
    }


    // API CALLS
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

        userRepository.findById(userUpdateRequest.getId()).ifPresentOrElse(foundUser -> {
            System.out.println(userUpdateRequest.getFirstname());
            System.out.println(userUpdateRequest.getId());

            PasswordConfig
            System.out.println(userUpdateRequest.getPassword());
            System.out.println(userUpdateRequest.getLastname());
            System.out.println(userUpdateRequest.getEmail());

            foundUser.setFirstname( userUpdateRequest.getFirstname());
            foundUser.setLastname( userUpdateRequest.getLastname());
            foundUser.setPassword( userUpdateRequest.getPassword());
            foundUser.setEmail( userUpdateRequest.getEmail());

            System.out.printf(foundUser.getPassword() + foundUser.getEmail() + foundUser.getLastname(),
                    foundUser.getFirstname() + foundUser.getId());

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

    // PRIVATE METHODS
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
