package com.social.join.controllers;

import com.social.join.bootstrapData.DummyData;
import com.social.join.bootstrapData.DummyDataGenerator;
import com.social.join.dtos.PostDTO;
import com.social.join.dtos.UserCreateRequest;
import com.social.join.dtos.UserDTO;
import com.social.join.dtos.UserUpdateRequest;
import com.social.join.mappers.IPostMapper;
import com.social.join.mappers.IUserMapper;
import com.social.join.repositories.IPostRepository;
import com.social.join.repositories.IUserRepository;
import com.social.join.service.IPostService;
import com.social.join.service.IUserService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

@SpringBootTest
@Import({DummyDataGenerator.class, DummyData.class})
class PostControllerTest {

    @MockBean
    private  IPostController postController;

    @Autowired
    private IPostService postService;

    @Autowired
    private IPostMapper postMapper;

    @Autowired
    private IPostRepository postRepository;

    @BeforeEach
    public void setup() {
        postController = new PostControllerImpl();
    }

    @Test
    @Transactional
    public void testGetAllPosts() {
        // actual users
        if (postRepository.findAll().isEmpty()){
            assert false;
        }
        List<PostDTO> posts = postRepository.findAll()
                .stream()
                .map(postMapper::postToPostDTO)
                .toList();

        Assertions.assertThat(posts)
                .allMatch(Objects::nonNull);

        // controller's users
        ResponseEntity<List<PostDTO>> response = postController.getAllPosts();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(posts.size(), Objects.requireNonNull(response.getBody()).size());
        assertEquals(posts.get(0), response.getBody().get(0));
    }

    @Test
    @Transactional
    public void testGetPostById() {
        PostDTO postToCheck = postMapper.postToPostDTO(postRepository.getReferenceById(0));
        assertThat(postToCheck).isNotNull();

        ResponseEntity<PostDTO> response = postController.getPostById(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertThat(Objects.requireNonNull(response.getBody()).getId()).isNotNull();
        assertPostDTOEquality(postToCheck, Objects.requireNonNull(response.getBody()));
    }

    @Test
    @Transactional
    @Rollback
    public void testCreateUser() {
        PostDTO postToCreate = postMapper.postToPostDTO(postRepository.getReferenceById(0));
        assertThat(postToCreate).isNotNull();
        postToCreate.setContent("UPDATED_CONTENT");

        ResponseEntity<PostDTO> response = postController.createPost(postToCreate);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertPostDTOEquality(postToCreate, Objects.requireNonNull(response.getBody()));
    }

    @Test
    @Transactional
    @Rollback
    public void testUpdatePost() {
        PostDTO postToUpdate = postMapper.postToPostDTO(postRepository.getReferenceById(0));
        assertThat(postToUpdate).isNotNull();
        postToUpdate.setContent("UPDATED_CONTENT");

        ResponseEntity<PostDTO> response = postController.updatePost(postToUpdate.getId(), postToUpdate);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertPostDTOEquality(postToUpdate, Objects.requireNonNull(response.getBody()));
    }

    @Test
    @Transactional
    @Rollback
    public void testDeletePost() {

        ResponseEntity<Boolean> response = postController.deletePost(1);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertThat(response.getBody()).isTrue();

        assertThat(postService.deletePostById(1)).isFalse();    // not existing anymore
    }

    private void assertPostDTOEquality(PostDTO actualPostDTO, PostDTO postDTO) {
        assertEquals(actualPostDTO.getId(), postDTO.getId());
        assertEquals(actualPostDTO.getContent(), postDTO.getContent());
        assertEquals(actualPostDTO.getComments(), postDTO.getComments());
        assertEquals(actualPostDTO.getUserCreated(), postDTO.getUserCreated());
        assertEquals(actualPostDTO.getUsersWhoLikedThisPost(), postDTO.getUsersWhoLikedThisPost());
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