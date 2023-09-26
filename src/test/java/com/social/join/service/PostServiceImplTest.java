package com.social.join.service;

import com.social.join.dtos.CommentDTO;
import com.social.join.dtos.PostDTO;
import com.social.join.dtos.UserDTO;
import com.social.join.entities.Post;
import com.social.join.entities.User;
import com.social.join.mappers.IPostMapper;
import com.social.join.mappers.IUserMapper;
import com.social.join.repositories.IPostRepository;
import com.social.join.repositories.IUserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.anyOf;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PostServiceImplTest {

    @Autowired
    private IPostService postService;
    @Autowired
    private IPostMapper postMapper;
    @Autowired
    private IPostRepository postRepository;
    @Autowired
    private IUserRepository userRepository;
    @Autowired
    private IUserMapper userMapper;

    private PostDTO testPost;
    private User testUser;
    private UserDTO testUserDTO;

    @BeforeAll
    void setUp() {
        testUser = userRepository.findAll().get(0);
        testPost.setUserCreated(testUserDTO);
        testPost = postMapper.postToPostDTO(postRepository.findAll().get(0));
        testUserDTO = userMapper.userToUserDTO(userRepository.findAll().get(0));
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    @Transactional
    @Rollback
    void createPost() {
        String TEST_CONTENT = "TEST_CONTENT";
        LocalDateTime timeNow = LocalDateTime.now();

        testPost.setUserCreated(testUserDTO);
        testPost.setId(null);
        testPost.setContent(TEST_CONTENT);
        testPost.setCreatedDate(timeNow);
        testPost.setUpdatedDate(timeNow);

        PostDTO returnedPost = postService.createPost(testUser, testPost);
        assertThat(returnedPost).isNotNull();
        assertThat(returnedPost.getId()).isNotNull();
        assertThat(returnedPost.getUserCreated().getId()).isEqualTo(testUser.getId());
        assertThat(returnedPost.getContent()).isEqualTo(TEST_CONTENT);
        assertThat(returnedPost.getCreatedDate().toString()).isEqualTo(timeNow.toString());
        assertThat(returnedPost.getUpdatedDate().toString()).isEqualTo(timeNow.toString());

    }

    @Test
    @Transactional
    @Rollback
    void createAndGetPostsByUserId() {
        // Create a test post
        String TEST_CONTENT = "TEST_CONTENT";
        LocalDateTime timeNow = LocalDateTime.now();
        testPost.setUserCreated(testUserDTO);
        testPost.setId(null);
        testPost.setContent(TEST_CONTENT);
        testPost.setCreatedDate(timeNow);
        testPost.setUpdatedDate(timeNow);

        // Save -- a dummy post
        // No service calls
        PostDTO returnedPost = postMapper.postToPostDTO(postRepository.save(postMapper.postDTOToPost(testPost)));
        assertThat(returnedPost).isNotNull();
        assertThat(returnedPost.getId()).isNotNull();
        assertThat(returnedPost.getContent()).isEqualTo(TEST_CONTENT);
        assertThat(returnedPost.getUserCreated().getId()).isEqualTo(testUser.getId());
        assertThat(returnedPost.getCreatedDate().toString()).isEqualTo(timeNow.toString());
        assertThat(returnedPost.getUpdatedDate().toString()).isEqualTo(timeNow.toString());

        // Get -- All Posts by User ID
        // Service Calls
        List<PostDTO> posts = postService.getPostsByUserId(testUser.getId());
        assertThat(posts).isNotNull();
        assertThat(posts.size()).isEqualTo(1);
        assertThat(posts.get(0).getId()).isEqualTo(returnedPost.getId());
        assertThat(posts.get(0).getUserCreated().getId()).isEqualTo(testUser.getId());
        assertThat(posts.get(0).getContent()).isEqualTo(TEST_CONTENT);
    }

    @Test
    void getPostById() {
         postService.getPostById(testPost.getId()).ifPresent(p -> {
             assertThat(p).isNotNull();
             assertThat(p.getId()).isEqualTo(testPost.getId());
             assertThat(p.getUserCreated().getId()).isNotNull();
                 }
         );

    }

    @Test
    @Transactional
    @Rollback
    void deletePostById() {
        boolean deleteStatus = postService.deletePostById(testPost.getId());
        assertThat(deleteStatus).isTrue();
        assertThat(postService.getPostById(testPost.getId())).isEmpty();    // returns Optional.empty()
        // TODO : Test that recursively all the comments of that post are deleted
    }

    @Test
    @Transactional
    @Rollback
    void updatePostById() {
        String UPDATED_CONTENT = "UPDATED_CONTENT";
        testPost.setContent(UPDATED_CONTENT);
        testPost.setUserCreated(testUserDTO);
        testPost.setLikedByUsers(Set.of(testUserDTO));

        // Save the Post first
        postRepository.save(postMapper.postDTOToPost(testPost));

        postService.updatePostById(testPost.getId(), testPost).ifPresent((p) ->{
           assertThat(p).isNotNull();
           assertThat(p.getContent()).isEqualTo(UPDATED_CONTENT);
           assertThat(p.getLikedByUsers().size()).isEqualTo(1);
           assertThat(p.getUserCreated().getId()).isEqualTo(testUserDTO.getId());
        });

    }

    @Test
    void getAllTheCommentsByPostId() {
    }

    @Test
    void getAllTheHashtagsByPostId() {
    }

    @Test
    void getUsersThatLikedByPostId() {
    }
}