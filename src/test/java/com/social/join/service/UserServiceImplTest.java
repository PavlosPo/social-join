package com.social.join.service;

import com.social.join.dtos.UserDTO;
import com.social.join.entities.Post;
import com.social.join.repositories.IPostRepository;

import jakarta.validation.constraints.NotNull;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;



@SpringBootTest
class UserServiceImplTest {

    @Autowired
    private IUserService userService;

    @Autowired
    private IPostRepository postRepository;

    @Test
    @Transactional
    void getUsersBySearchTest() {
        // Arrange and Act
        UserDTO testUser = userService.getAllUsers().get(1);

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
        List<UserDTO> testUsers = userService.getAllUsers();
        UserDTO testUser = testUsers.get(1);
        assertThat(testUsers).isNotEmpty();

        // Act
        UserDTO testUserFromService = userService.getUserById(testUser.getId()).orElse(null);

        // Assert
        assertThat(testUserFromService).isNotNull();
        assertThat(testUserFromService.getUsername()).isEqualTo(testUser.getUsername());
    }

    @Test
    @Rollback
    @Transactional
    void deleteUserTest() {
        // Arrange
        UserDTO testUser = userService.getAllUsers().get(0);

        // Act
        boolean deleteStatus = userService.deleteById(testUser.getId());

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
        UserDTO testUser = getUserDTO(testPostToAdd);   // adds a post to the user

        // Act
        UserDTO userReturned = userService.saveNewUser(testUser);

        // Assert
        assertUserEquality(userReturned, testUser, false);
    }

    @Test
    @Rollback
    @Transactional
    void updateUserTest() {
        UserDTO testUser = userService.getAllUsers().get(0);
        // Arrange
        testUser.setUsername("UpdatedUsername");
        testUser.setPassword("updatedPassword");
        testUser.setLastname("updatedLastname");
        testUser.setFirstname("updatedFirstname");
        testUser.setEmail("updatedEmail@test.com");
        testUser.setVersion(2);

        // Act
        Optional<UserDTO> updatedUser = userService.updateUserById(testUser.getId(), testUser);

        // Assert
        assertThat(updatedUser).isNotEmpty();
        assertUserEquality(updatedUser.get(), testUser, true);
    }

    @Test
    void getAllLikedPostsTest() {

    }

    @NotNull
    private UserDTO getUserDTO(Post testPostToAdd) {
        UserDTO testUser = userService.getAllUsers().get(0);
        String PASSWORD = "CREATED_PASSWORD";
        String EMAIL = "TEST@TEST.COM";

        testUser.setId(null);
        testUser.setUsername("testUsername");
        testUser.setFirstname("testFirstname");
        testUser.setLastname("testLastname");
        testUser.setPassword(PASSWORD);
        testUser.setVersion(1);
        testUser.setLikedPosts(Set.of(testPostToAdd));
        testUser.setEmail(EMAIL);
        return testUser;
    }

    private void assertUserSearch(String username, String firstname, String lastname) {
        List<UserDTO> usersList = userService.getUsersBySearch(username , firstname, lastname);
        assertThat(usersList).isNotEmpty();
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
        assertThat(actualUser.getVersion()).isEqualTo(expectedUser.getVersion());
        assertThat(actualUser.getLikedComments().size()).isEqualTo(expectedUser.getLikedComments().size());
        assertThat(actualUser.getLikedComments()).isEqualTo(expectedUser.getLikedComments());
        assertThat(actualUser.getLikedPosts().size()).isEqualTo(expectedUser.getLikedPosts().size());
        assertThat(actualUser.getLikedPosts()).isEqualTo(expectedUser.getLikedPosts());
    }
}