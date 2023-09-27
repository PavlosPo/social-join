package com.social.join.service;

import com.social.join.dtos.PostDTO;
import com.social.join.dtos.UserDTO;
import com.social.join.entities.Post;
import com.social.join.entities.User;
import com.social.join.mappers.IUserMapper;
import com.social.join.repositories.IUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Optional;
import java.util.Set;
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
    private IUserMapper userMapper;

    private PostDTO testPost;
    private User testUser;
    private UserDTO testUserDTO;

    @BeforeEach
    void setUp() {
        // Initialize testUser and testUserDTO
        testUser = userRepository.findAll().get(0);
        testUserDTO = userMapper.userToUserDTO(testUser);

        // Initialize testPost
        testPost = new PostDTO();
        testPost.setUserCreated(testUserDTO);
        testPost.setContent("Initial Content");

        // Save the testPost to the database
        testPost = postService.savePost(testPost);
    }

    @Test
    @Transactional
    @Rollback
    void createPost() {
        // Arrange
        String updatedContent = "TEST_CONTENT";
        testPost.setId(null);
        testPost.setContent(updatedContent);

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
        testPost.setLikedByUsers(Set.of(testUserDTO));

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
        testPost.setLikedByUsers(Set.of(
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

        Set<Integer> actualLikedUserIds = actualPost.getLikedByUsers() != null
                ? actualPost.getLikedByUsers().stream()
                    .map(UserDTO::getId)
                    .collect(Collectors.toSet())
                : new HashSet<>();

        Set<Integer> expectedLikedUserIds = expectedPost.getLikedByUsers() != null
                ? expectedPost.getLikedByUsers().stream()
                    .map(UserDTO::getId)
                    .collect(Collectors.toSet())
                : new HashSet<>();

        assertThat(actualLikedUserIds.size()).isEqualTo(expectedLikedUserIds.size());
        assertThat(actualLikedUserIds).isEqualTo(expectedLikedUserIds);

    }
}