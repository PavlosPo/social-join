package com.social.join.service;

import com.social.join.dtos.UserDTO;
import com.social.join.entities.Comment;
import com.social.join.entities.Post;
import com.social.join.mappers.ICommentMapper;
import com.social.join.mappers.IPostMapper;
import com.social.join.mappers.IUserMapper;
import com.social.join.repositories.ICommentRepository;
import com.social.join.repositories.IPostRepository;
import com.social.join.repositories.IUserRepository;

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
    private IUserRepository userRepository;

    @Autowired
    private IUserMapper userMapper;

    // Post
    @Autowired
    private IPostRepository postRepository;

    @Autowired
    private IPostMapper postMapper;

    // Comments
    @Autowired
    private ICommentRepository commentRepository;

    @Autowired
    private ICommentMapper commentMapper;


    @Test
    @Transactional
    void getUsersBySearchTest() {
        UserDTO testUser = userService.getAllUsers().get(1);
        assertThat(testUser).isNotNull();

        String FIRSTNAME = testUser.getFirstname();
        String LASTNAME = testUser.getLastname();
        String USERNAME = testUser.getUsername();

        List<UserDTO> usersListWithFirstname = userService.getUsersBySearch(null, FIRSTNAME, null);
        assertThat(usersListWithFirstname.size()).isEqualTo(1);
        assertThat(usersListWithFirstname.get(0).getId()).isEqualTo(testUser.getId());


        List<UserDTO> usersListWithLastname = userService.getUsersBySearch(null, null, LASTNAME);
        assertThat(usersListWithLastname.size()).isEqualTo(1);
        assertThat(usersListWithLastname.get(0).getId()).isEqualTo(testUser.getId());

        List<UserDTO> usersListWithUsername = userService.getUsersBySearch(USERNAME, null, null);
        assertThat(usersListWithUsername.size()).isEqualTo(1);
        assertThat(usersListWithUsername.get(0).getId()).isEqualTo(testUser.getId());

        List<UserDTO> usersListWithUsername2 = userService.getUsersBySearch("username", null, null);    // all users that starts with '%username%'
        assertThat(usersListWithUsername2.size()).isEqualTo(userRepository.findAll().size());

        List<UserDTO> usersListWithLastname2 = userService.getUsersBySearch(null, null, "lastname");    // all users that starts with '%lastname%'
        assertThat(usersListWithLastname2.size()).isEqualTo(userRepository.findAll().size());

        List<UserDTO> usersListWithFirstname2 = userService.getUsersBySearch(null, "user", null);    // all users that starts with '%user%'
        assertThat(usersListWithFirstname2.size()).isEqualTo(userRepository.findAll().size());

        List<UserDTO> usersListWithUsernameAndFirstname = userService.getUsersBySearch("username", "user0", null);    // all users that starts with '%username%'
        assertThat(usersListWithUsernameAndFirstname.size()).isEqualTo(11);

        List<UserDTO> usersListWithFirstnameAndLastname = userService.getUsersBySearch(null, "user0", "lastname0");    // all users that starts with '%username%'
        assertThat(usersListWithFirstnameAndLastname.size()).isEqualTo(2);

        List<UserDTO> usersListWithAll = userService.getUsersBySearch("username0", "user0", "lastname0");    // all users that starts with '%username%'
        assertThat(usersListWithAll.size()).isEqualTo(3);
    }

    @Test
    @Transactional
    void getAllUsersTest() {
        List<UserDTO> testUsers =  userRepository.findAll().stream().map(userMapper::userToUserDTO).toList();
        List<UserDTO> testUsersFromService = userService.getAllUsers();

        assertThat(testUsersFromService.size()).isEqualTo(testUsers.size());
        assertThat(testUsersFromService.get(1).getUsername()).isEqualTo(testUsers.get(1).getUsername());
    }

    @Test
    @Rollback
    @Transactional
    void deleteUserTest() {
        UserDTO testUser = userService.getAllUsers().get(0);
        boolean deleteStatus = userService.deleteById(testUser.getId());
        assertThat(deleteStatus).isTrue();
        assertThat(userService.getUserById(testUser.getId())).isEmpty();
    }

    @Test
    @Rollback
    @Transactional
    void createUserTest() {
        Post testPostToAdd = postRepository.findAll().get(0);
        UserDTO testUser = getUserDTO(testPostToAdd);


        UserDTO userReturned = userService.saveNewUser(testUser);
        assertThat(userReturned.getId()).isNotNull().isNotEqualTo(testUser.getId());
        assertThat(userReturned.getUsername()).isEqualTo(testUser.getUsername());
        assertThat(userReturned.getLastname()).isEqualTo(testUser.getLastname());
        assertThat(userReturned.getFirstname()).isEqualTo(testUser.getFirstname());
        assertThat(userReturned.getPassword()).isEqualTo(testUser.getPassword());
        assertThat(userReturned.getEmail()).isEqualTo(testUser.getEmail());
        assertThat(userReturned.getVersion()).isEqualTo(testUser.getVersion());
        assertThat(userReturned.getLikedComments().size()).isEqualTo(testUser.getLikedComments().size());
        assertThat(userReturned.getLikedComments()).isEqualTo(testUser.getLikedComments());
        assertThat(userReturned.getLikedPosts().size()).isEqualTo(testUser.getLikedPosts().size());
        assertThat(userReturned.getLikedPosts()).isEqualTo(testUser.getLikedPosts());
//        assertThat(userReturned.getPostsMadeByUser().size()).isEqualTo(testUser.getPostsMadeByUser().size());
    }

    @Test
    @Rollback
    @Transactional
    void updateUserTest() {
        UserDTO testUser = userService.getAllUsers().get(0);
        testUser.setUsername("UpdatedUsername");
        testUser.setPassword("updatedPassword");
        testUser.setLastname("updatedLastname");
        testUser.setFirstname("updatedFirstname");
        testUser.setEmail("updatedEmail@test.com");
        testUser.setVersion(2);

        Optional<UserDTO> updatedUser = userService.updateUserById(testUser.getId(), testUser);
        assertThat(updatedUser).isNotEmpty();
        assertThat(updatedUser.get().getId()).isEqualTo(testUser.getId());
        assertThat(updatedUser.get().getUsername()).isEqualTo(testUser.getUsername());
        assertThat(updatedUser.get().getVersion()).isEqualTo(testUser.getVersion());
        assertThat(updatedUser.get().getEmail()).isEqualTo(testUser.getEmail());
        assertThat(updatedUser.get().getLastname()).isEqualTo(testUser.getLastname());
        assertThat(updatedUser.get().getFirstname()).isEqualTo(testUser.getFirstname());
        assertThat(updatedUser.get().getPassword()).isEqualTo(testUser.getPassword());
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
        //testUser.setLikedComments(Set.of(testPostToAdd.getComments().)));
        testUser.setEmail(EMAIL);
        //testUser.setPostsMadeByUser(Set.of(postMapper.postToPostDTO(testPostToAdd)));
        return testUser;
    }
}