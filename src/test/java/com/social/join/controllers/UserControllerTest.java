package com.social.join.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.social.join.dtos.UserCreateRequest;
import com.social.join.dtos.UserDTO;
import com.social.join.dtos.UserUpdateRequest;
import com.social.join.entities.User;
import com.social.join.service.IUserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
class UserControllerTest {

    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    WebApplicationContext wac;

    @Autowired
    private IUserService userService;

//    @Autowired
//    private IUserController userController;

    private UserDTO userToHelp;
    private Integer userIdToHelp;

    @BeforeEach
    @Transactional
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
        userToHelp = createUserAndReturnUser("FIRSTNAME", "LASTNAME", "EXAMPLE@EXAMPLE.COM", "PASSWORD", "USERNAME");
        userIdToHelp = userToHelp.getId();
    }

    @AfterEach
    @Transactional
    void tearDown() {
        if (userIdToHelp != null) {
            userService.deleteUser(userIdToHelp);
        }
    }

    @Captor
    ArgumentCaptor<Integer> idArgumentCaptor;

    @Captor
    ArgumentCaptor<User> userArgumentCaptor;

    @Test
    @Transactional
    public void shouldReturnAllUsers() throws Exception {
        // Arrange, Act, Assert
        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content.size()").isNotEmpty())
                .andExpect(jsonPath("$.content.size()").isNumber());
    }

    @Test
    public void shouldReturnUserById() throws Exception {
        // Arrange, Act, Assert
        mockMvc.perform(get("/users/" + userIdToHelp))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.id").value(userIdToHelp));
    }

    @Test
    @Transactional
    public void shouldCreateUser() throws Exception {
        // Arrange
        UserCreateRequest userCreateRequest = createUserRequest("FIRSTNAME", "LASTNAME", "EXAPLES@EXAMPLE.COM", "PASSWORD", "USERNAME");

        // Act
        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userCreateRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.firstname").value("FIRSTNAME"))
                .andExpect(jsonPath("$.lastname").value("LASTNAME"))
                .andExpect(jsonPath("$.email").value("EXAPLES@EXAMPLE.COM"))
                .andExpect(jsonPath("$.password").value("PASSWORD"))
                .andExpect(jsonPath("$.username").value("USERNAME"));
    }

    @Test
    @Transactional
    public void shouldUpdateUser() throws Exception {
        // Arrange
        UserUpdateRequest userUpdateRequest = updateUserRequest(userIdToHelp, "EXAPLES@EXAMPLE.COM", "UPDATED_FIRSTNAME", "UPDATED_LASTNAME", "UPDATED_PASSWORD", "UPDATED_USERNAME");

        // Act and Assert
        mockMvc.perform(patch("/users/" + userIdToHelp)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userUpdateRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(userIdToHelp))
                .andExpect(jsonPath("$.firstname").value("UPDATED_FIRSTNAME"))
                .andExpect(jsonPath("$.lastname").value("UPDATED_LASTNAME"))
                .andExpect(jsonPath("$.username").value("UPDATED_USERNAME"))
                .andExpect(jsonPath("$.password").value("UPDATED_PASSWORD"))
                .andExpect(jsonPath("$.email").value("EXAPLES@EXAMPLE.COM"));
    }

    @Test
    @Transactional
    public void shouldDeleteUser() throws Exception {
        // Arrange (Already done in @BeforeEach)

        // Act
        mockMvc.perform(delete("/users/" + userIdToHelp))
                .andExpect(status().isNoContent());

        // Assert
        // You can add assertions to check that the user has been deleted, e.g., make a request to get the user by ID and expect a 404 response.
    }

    private Integer createUserAndReturnId(String firstname, String lastname, String email, String password, String username) {
        UserCreateRequest userCreateRequest = createUserRequest(firstname, lastname, email, password, username);
        return userService.createUser(userCreateRequest).getId();
    }

    private UserDTO createUserAndReturnUser(String firstname, String lastname, String email, String password, String username) {
        UserCreateRequest userCreateRequest = createUserRequest(firstname, lastname, email, password, username);
        return userService.createUser(userCreateRequest);
    }

    private UserCreateRequest createUserRequest(String firstname, String lastname, String email, String password, String username) {
        return UserCreateRequest.builder()
                .firstname(firstname)
                .lastname(lastname)
                .email(email)
                .password(password)
                .username(username)
                .build();
    }

    private UserUpdateRequest updateUserRequest(Integer id, String email, String firstname, String lastname, String password, String username) {
        return UserUpdateRequest.builder()
                .id(id)
                .email(email)
                .firstname(firstname)
                .lastname(lastname)
                .password(password)
                .username(username)
                .build();
    }
}