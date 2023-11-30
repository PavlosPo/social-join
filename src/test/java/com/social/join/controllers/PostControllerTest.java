package com.social.join.controllers;

import com.social.join.bootstrapData.DummyData;
import com.social.join.bootstrapData.DummyDataGenerator;
import com.social.join.dtos.PostDTO;
import com.social.join.dtos.UserDTO;
import com.social.join.mappers.IPostMapper;
import com.social.join.repositories.IPostRepository;
import com.social.join.service.IPostService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Import({DummyDataGenerator.class, DummyData.class})
class PostControllerTest {

    @MockBean
    private IPostController postController;

    @Autowired
    private IPostService postService;

    @Autowired
    private IPostMapper postMapper;

    @Autowired
    private IPostRepository postRepository;

    @BeforeEach
    public void setup() {
        postController = new PostControllerImpl(postRepository, postService, postMapper);
    }

    @Test
    @Transactional
    public void testGetAllPosts() {
        // Actual posts from the repository
        List<PostDTO> actualPosts = postRepository.findAll()
                .stream()
                .map(postMapper::postToPostDTO)
                .toList();

        Assertions.assertThat(actualPosts)
                .allMatch(Objects::nonNull);

        // Controller's posts
        ResponseEntity<List<PostDTO>> response = postController.getAllPosts();

        assertEquals(HttpStatus.OK, response.getStatusCode());

        List<PostDTO> controllerPosts = response.getBody();
        assertNotNull(controllerPosts);

        // Check that the sizes match
        assertEquals(actualPosts.size(), controllerPosts.size());

        // Check that each post in the controller's response is present in the actual posts
        for (int i = 0; i < actualPosts.size(); i++) {
            assertEquals(actualPosts.get(i).getId(), controllerPosts.get(i).getId());
            // Add more assertions as needed for other fields
        }
    }

    @Test
    @Transactional
    public void testGetPostById() {
        PostDTO postToCheck = postMapper.postToPostDTO(postRepository.findById(1).orElse(null));
        assertThat(postToCheck).isNotNull();

        ResponseEntity<PostDTO> response = postController.getPostById(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertThat(Objects.requireNonNull(response.getBody()).getId()).isNotNull();
        assertPostDTOEquality(postToCheck, Objects.requireNonNull(response.getBody()));
    }

    @Test
    @Transactional
    public void testCreatePost() {
        PostDTO postToCreate = postMapper.postToPostDTO(postRepository.findById(1).orElse(null));
//        System.out.println(postToCreate);
        assertThat(postToCreate).isNotNull();
        postToCreate.setContent("UPDATED_CONTENT");
        postToCreate.setId(null);

        ResponseEntity<PostDTO> response = postController.createPost(postMapper.postDTOToPostCreateRequest(postToCreate));

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertThat(response).isNotNull();
        assertThat(response.getBody().getId()).isNotNull();
        assertThat(response.getBody().getContent()).isNotNull();
        assertThat(response.getBody().getContent()).isEqualTo(postToCreate.getContent());
        assertThat(response.getBody().getUserCreated().getId()).isEqualTo(postToCreate.getUserCreated().getId());
    }

    @Test
    @Transactional
    @Rollback
    public void testUpdatePost() {
        PostDTO postToUpdate = postMapper.postToPostDTO(postRepository.findById(1).orElse(null));
        assertThat(postToUpdate).isNotNull();
        postToUpdate.setContent("UPDATED_CONTENT");


        ResponseEntity<PostDTO> response = postController.updatePost(postToUpdate.getId(), postMapper.postDTOToPostUpdateRequest(postToUpdate));

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertPostDTOEquality(postToUpdate, Objects.requireNonNull(response.getBody()));
    }

    @Test
    @Transactional
    @Rollback
    public void testDeletePost() {

        ResponseEntity<Boolean> response = postController.deletePost(1);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        assertThat(response.getBody()).isNull();

        assertThat(postService.deletePostById(1)).isFalse();    // not existing anymore
    }

    private void assertPostDTOEquality(PostDTO actualPostDTO, PostDTO postDTO) {
        assertEquals(actualPostDTO.getId(), postDTO.getId());
        assertEquals(actualPostDTO.getContent(), postDTO.getContent());
//        assertEquals(actualPostDTO.getComments(), postDTO.getComments());
        assertEquals(actualPostDTO.getUserCreated().getId(), postDTO.getUserCreated().getId());
        assertEquals(actualPostDTO.getUsersWhoLikedThisPost().size(), postDTO.getUsersWhoLikedThisPost().size());
        assertEquals(actualPostDTO.getCreatedDate(), postDTO.getCreatedDate());
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