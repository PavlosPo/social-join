package com.social.join.service;

import com.social.join.dtos.PostDTO;
import com.social.join.dtos.UserDTO;
import com.social.join.mappers.IPostMapper;
import com.social.join.mappers.IUserMapper;
import com.social.join.repositories.IPostRepository;
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
    private IPostRepository postRepository;
    @Autowired
    private IUserMapper userMapper;
    @Autowired
    private IPostMapper postMapper;

    private PostDTO testPost;
    private UserDTO testUserDTO;

    @BeforeEach
    @Transactional
    void setUp() {
        testPost = postMapper.postToPostDTO(postRepository.getReferenceById(1));
        testUserDTO = testPost.getUserCreated();
    }

    @AfterEach
    void tearDown() {
        testPost = null;
        testUserDTO = null ;
    }

    @Test
    @Transactional
    void createPost() {
        // Arrange
        testPost.setId(null);
        testPost.setUserCreated(testUserDTO);
        testPost.setCreatedDate(null);
        testPost.setUpdateDate(null);
        testPost.setUsersWhoLikedThisPost(null);
//        testPost.setComments(null);
        testPost.setContent("UPDATED_CONTENT");

        // Act
        PostDTO returnedPost = postService.createPost(postMapper.postDTOToPostCreateRequest(testPost));

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
        Optional<PostDTO> updatedPostOptional = postService.updatePostById(testPost.getId(), postMapper.postDTOToPostUpdateRequest(testPost));

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
        Optional<PostDTO> updatedPostOptional = postService.updatePostById(testPost.getId(), postMapper.postDTOToPostUpdateRequest(testPost));

        // Assert
        assertThat(updatedPostOptional).isPresent()
                .hasValueSatisfying(postDTO -> assertPostEquality(postDTO, testPost));

    }

    private void assertPostEquality(PostDTO actualPost, PostDTO expectedPost){
        assertThat(actualPost.getId()).isNotNull();
        assertThat(actualPost.getContent()).isEqualTo(expectedPost.getContent());
//        assertThat(actualPost.getComments()).isEqualTo(expectedPost.getComments());
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