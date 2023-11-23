package com.social.join.service;

import com.social.join.dtos.PostDTO;
import com.social.join.dtos.UserCreateRequest;
import com.social.join.dtos.UserDTO;
import com.social.join.entities.Post;
import com.social.join.mappers.ICreateUserRequestMapper;
import com.social.join.mappers.IPostMapper;
import com.social.join.mappers.IUpdateUserRequestMapper;

import com.social.join.repositories.IPostRepository;
import jakarta.validation.constraints.NotNull;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;



@SpringBootTest
class UserServiceImplTest {

    @Autowired
    private IUserService userService;

    @Autowired
    private IPostRepository postRepository;

    @Autowired
    private IPostMapper postMapper;

    @Autowired
    private ICreateUserRequestMapper createUserRequestMapper;

    @Autowired
    private IUpdateUserRequestMapper updateUserRequestMapper;

    @Test
    @Transactional
    void getUsersBySearchTest() {
        // Arrange and Act
        UserDTO testUser = userService.getAllUsers(null, null, null, null, null).getContent().get(1);

        // Assert
        assertThat(testUser).isNotNull();
        assertUserSearch(null, null, testUser.getLastname());
        assertUserSearch(testUser.getUsername(), null, null);
        assertUserSearch(null, testUser.getFirstname(), null);
    }

    @Test
    @Transactional
    void getAllUsersTest() {
        // Arrange
        Page<UserDTO> pageTestUsers = userService.getAllUsers(null, null, null, null, null);
        UserDTO testUsers = pageTestUsers.getContent().get(0);
        assertThat(testUsers).isNotNull();

        // Act
        UserDTO testUserFromService = userService.getUserById(testUsers.getId()).orElse(null);

        // Assert
        assertThat(testUserFromService).isNotNull();
        assertThat(testUserFromService.getUsername()).isEqualTo(testUsers.getUsername());
    }

    @Test
    @Rollback
    @Transactional
    void deleteUserTest() {
        // Arrange
        UserDTO testUser = userService.getAllUsers(null, null, null, null, null).getContent().get(0);

        // Act
        boolean deleteStatus = userService.deleteUser(testUser.getId());

        // Assert
        assertThat(deleteStatus).isTrue();
        assertThat(userService.getUserById(testUser.getId())).isEmpty();
    }

    @Test
    @Rollback
    @Transactional
    void createUserTest() {
        // Arrange
        Post testPostToAdd = postRepository.findAll().get(0);

        UserDTO testUserDTO = getUserDTO(postMapper.postToPostDTO(testPostToAdd));   // adds a post to the user
        UserCreateRequest testUserCreateRequest =  createUserRequestMapper.UserDTOToUserCreateRequest(testUserDTO);

        // Act
        UserDTO userReturned = userService.createUser(testUserCreateRequest);

        // Assert
        assertUserEquality(userReturned, userReturned, false);
    }

    @Test
    @Rollback
    @Transactional
    void updateUserTest() {
        UserDTO testUser = userService.getAllUsers(null, null, null, null, null).getContent().get(0);
        // Arrange
        testUser.setUsername("UpdatedUsername");
        testUser.setPassword("updatedPassword");
        testUser.setLastname("updatedLastname");
        testUser.setFirstname("updatedFirstname");
        testUser.setEmail("updatedEmail@test.com");

        // Act
        Optional<UserDTO> updatedUser = userService.updateUser(testUser.getId(), updateUserRequestMapper.UserDTOToUserUpdateRequest(testUser));

        // Assert
        assertThat(updatedUser).isNotEmpty();
        assertUserEquality(updatedUser.get(), testUser, true);
    }

    @Test
    void getAllLikedPostsTest() {

    }

    @NotNull
    private UserDTO getUserDTO(PostDTO testPostToAdd) {
        UserDTO testUser = userService.getAllUsers(null, null, null, null, null).getContent().get(0);
        String PASSWORD = "CREATED_PASSWORD";
        String EMAIL = "TEST@TEST.COM";

        testUser.setId(null);
        testUser.setUsername("testUsername");
        testUser.setFirstname("testFirstname");
        testUser.setLastname("testLastname");
        testUser.setPassword(PASSWORD);
        testUser.addLikedPost(testPostToAdd);
        testUser.setEmail(EMAIL);
        return testUser;
    }

    private void assertUserSearch(String username, String firstname, String lastname) {
        Page<UserDTO> usersList = userService.getAllUsers(username , firstname, lastname, 1, 25);
        assertThat(usersList).isNotNull();
    }

    /**
     * Assert the actualUser and the expectedUser. Also given true or false checks the ids.
     * @param actualUser    the {@link UserDTO} as the actualUser
     * @param expectedUser  the {@link UserDTO} as the expectedUser
     * @param checkID       true to check the id, false to not
     */
    private void assertUserEquality(UserDTO actualUser, UserDTO expectedUser, boolean checkID) {
        assertThat(actualUser.getId()).isNotNull();
        if (checkID) assertThat(actualUser.getId()).isEqualTo(expectedUser.getId());
        assertThat(actualUser.getUsername()).isEqualTo(expectedUser.getUsername());
        assertThat(actualUser.getLastname()).isEqualTo(expectedUser.getLastname());
        assertThat(actualUser.getFirstname()).isEqualTo(expectedUser.getFirstname());
        assertThat(actualUser.getPassword()).isEqualTo(expectedUser.getPassword());
        assertThat(actualUser.getEmail()).isEqualTo(expectedUser.getEmail());
    }
}