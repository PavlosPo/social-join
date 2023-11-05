package com.social.join.service;

import com.social.join.dtos.PostDTO;
import com.social.join.dtos.UserDTO;
import com.social.join.entities.Post;
import com.social.join.entities.User;
import com.social.join.mappers.IPostMapper;
import com.social.join.mappers.IUserMapper;
import com.social.join.repositories.IPostRepository;
import com.social.join.repositories.IUserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.anyOf;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
class PostServiceImplTest {

    @Autowired
    private IPostService postService;
    @Autowired
    private IUserRepository userRepository;
    @Autowired
    private IPostRepository postRepository;
    @Autowired
    private IUserMapper userMapper;
    @Autowired
    private IPostMapper postMapper;

    private PostDTO testPost;
    private User testUser;
    private UserDTO testUserDTO;

    @BeforeEach
    @Transactional
    void setUp() {
        testPost = postMapper.postToPostDTO(postRepository.getReferenceById(1));
        testUser = userMapper.userDTOToUser(testPost.getUserCreated());
        // testUser.setVersion(postRepository.getReferenceById(1).getUserCreated().getVersion());
        testUserDTO = testPost.getUserCreated();
//        User user = userRepository.findById(1).orElseThrow(() -> new RuntimeException("User not found"));
//
//        // Create a new PostDTO and associate it with the fetched User
//        PostDTO testPost = new PostDTO();
//        testPost.setUserCreated(userMapper.userToUserDTO(user));
//        testPost.setContent("Initial Content");
//
//        // Save the testPost to the database
//        testPost = postService.savePost(testPost);
    }

    @AfterEach
    void tearDown() {
        testPost = null;
        testUser = null ;
        testUserDTO = null ;
    }

    @Test
    @Transactional
    void createPost() {
//        // Arrange
//        String updatedContent = "TEST_CONTENT";
//        testPost.setId(null);
//        testPost.setContent(updatedContent);
//
//        // Act
//        PostDTO returnedPost = postService.savePost(testPost);
//
//        // Assert
//        assertPostEquality(returnedPost, testPost);
        // Arrange
        testPost.setId(null);
        testPost.setUserCreated(testUserDTO);
        testPost.setCreatedDate(null);
        testPost.setUpdatedDate(null);
        testPost.setUsersWhoLikedThisPost(null);
        testPost.setComments(null);
        testPost.setContent("UPDATED_CONTENT");

        // Act
        PostDTO returnedPost = postService.savePost(testPost);

        // Assert
        assertPostEquality(returnedPost, testPost);
    }

    @Test
    @Transactional
    void getPostById() {
        // Act
        Optional<PostDTO> optionalPost = postService.getPostById(testPost.getId());

        // Assert
        assertThat(optionalPost).isPresent()
                .hasValueSatisfying(actualPost -> assertPostEquality(actualPost, testPost));
    }

    @Test
    @Transactional
    @Rollback
    void deletePostById() {
        // Act
        boolean deleteStatus = postService.deletePostById(testPost.getId());

        // Assert
        assertThat(deleteStatus).isTrue();
        assertThat(postService.getPostById(testPost.getId())).isEmpty();
    }

    @Test
    @Transactional
    @Rollback
    void updatePostById() {
        // Arrange
        String updatedContent = "UPDATED_CONTENT";
        testPost.setContent(updatedContent);

        // Act
        Optional<PostDTO> updatedPostOptional = postService.updatePostById(testPost.getId(), testPost);

        // Assert
        assertThat(updatedPostOptional).isPresent()
                .hasValueSatisfying(updatedPost -> assertPostEquality(updatedPost, testPost));
    }

    @Test
    @Transactional
    void getUsersThatLikedByPostId() {
        // Arrange
        testPost.setUsersWhoLikedThisPost(List.of(
                testUserDTO
        ));

        // Act
        Optional<PostDTO> updatedPostOptional = postService.updatePostById(testPost.getId(), testPost);

        // Assert
        assertThat(updatedPostOptional).isPresent()
                .hasValueSatisfying(postDTO -> assertPostEquality(postDTO, testPost));

    }

    private void assertPostEquality(PostDTO actualPost, PostDTO expectedPost){
        assertThat(actualPost.getId()).isNotNull();
        assertThat(actualPost.getContent()).isEqualTo(expectedPost.getContent());
        assertThat(actualPost.getComments()).isEqualTo(expectedPost.getComments());
        assertThat(actualPost.getUserCreated().getId()).isEqualTo(expectedPost.getUserCreated().getId());

        // this is to count the num of other's user's ids
        Set<Integer> actualLikedUserIds = actualPost.getUsersWhoLikedThisPost() != null
                ? actualPost.getUsersWhoLikedThisPost().stream()
                    .map(UserDTO::getId)
                    .collect(Collectors.toSet())
                : new HashSet<>();

        // this is to count the num of other's user's ids
        Set<Integer> expectedLikedUserIds = expectedPost.getUsersWhoLikedThisPost() != null
                ? expectedPost.getUsersWhoLikedThisPost().stream()
                    .map(UserDTO::getId)
                    .collect(Collectors.toSet())
                : new HashSet<>();

        assertThat(actualLikedUserIds.size()).isEqualTo(expectedLikedUserIds.size());
    }
}