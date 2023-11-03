package com.social.join.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.social.join.dtos.UserCreateRequest;
import com.social.join.dtos.UserDTO;
import com.social.join.dtos.UserUpdateRequest;
import com.social.join.entities.User;
import com.social.join.mappers.IUserMapper;
import com.social.join.repositories.IUserRepository;
import com.social.join.service.IUserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserControllerImpl.class)
class UserControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    private IUserController userController;

    @Autowired
    private IUserService userService;

    @Autowired
    private IUserMapper userMapper;

    @Autowired
    private IUserRepository userRepository;

    @BeforeEach
    public void setup() {
        userController = new UserControllerImpl(userService);
    }

    @Captor
    ArgumentCaptor<Integer> idArgumentCaptor;

    @Captor
    ArgumentCaptor<User> userArgumentCaptor;

    @Test
    @Transactional
    public void testGetAllUsers() throws Exception {
        // actual users
        given(userService.getAllUsers(any(), any(), any(), any(), any()))
                .willReturn(userService.getAllUsers(null, null, null, 1, 25));

        mockMvc.perform(get(UserControllerImpl.USER_PATH)
                .queryParam("pageSize", "800"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
//                .andExpect(jsonPath("$.content.length()").value(3));
    }

    @Test
    @Transactional
    public void testGetUserById() {
        Optional<UserDTO> actualUser = userService.getUserById(1);

        ResponseEntity<UserDTO> response = userController.getUserById(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertThat(Objects.requireNonNull(response.getBody()).getId()).isNotNull();
        assertThat(actualUser).isNotEmpty();
        assertUserDTOEquality(actualUser.get(), Objects.requireNonNull(response.getBody()));
    }

    @Test
    @Transactional
    @Rollback
    public void testCreateUser() {
        UserCreateRequest userCreateRequest = userMapper.userDTOToUserCreateRequest(userService.getAllUsers(null, null, null, null, null).getContent().get(0));
        userCreateRequest.setFirstname("UPDATED_USERNAME");

        ResponseEntity<UserDTO> response = userController.createUser(userCreateRequest);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertUserDTOEquality(Objects.requireNonNull(response.getBody()), userCreateRequest);
    }

    @Test
    @Transactional
    public void testUpdateUser() {
        UserUpdateRequest updateRequest = new UserUpdateRequest();
        updateRequest.setFirstname("UPDATED_USERNAME");

        UserDTO actualUser = userService.getAllUsers(null, null, null, null, null).getContent().get(0);

        ResponseEntity<UserDTO> response = userController.updateUser(1, updateRequest);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertUserDTOEquality(actualUser, Objects.requireNonNull(response.getBody()));
    }

    @Test
    @Transactional
    @Rollback
    public void testDeleteUser() {

        ResponseEntity<Boolean> response = userController.deleteUser(1);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertThat(response.getBody()).isTrue();

        assertThat(userService.deleteUser(1)).isFalse();    // not existing anymore
    }

    private void assertUserDTOEquality(UserDTO userDTO, UserCreateRequest userCreateRequest) {
        assertEquals(userCreateRequest.getUsername(), userDTO.getUsername());
        assertEquals(userCreateRequest.getFirstname(), userDTO.getFirstname());
        assertEquals(userCreateRequest.getLastname(), userDTO.getLastname());
        assertEquals(userCreateRequest.getEmail(), userDTO.getEmail());
        assertEquals(userCreateRequest.getPassword(), userDTO.getPassword());
    }

    private void assertUserDTOEquality(UserDTO actualUserDTO, UserDTO userDTO) {
        assertEquals(actualUserDTO.getId(), userDTO.getId());
        assertEquals(actualUserDTO.getUsername(), userDTO.getUsername());
        assertEquals(actualUserDTO.getFirstname(), userDTO.getFirstname());
        assertEquals(actualUserDTO.getLastname(), userDTO.getLastname());
        assertEquals(actualUserDTO.getEmail(), userDTO.getEmail());
        assertEquals(actualUserDTO.getPassword(), userDTO.getPassword());
    }
}