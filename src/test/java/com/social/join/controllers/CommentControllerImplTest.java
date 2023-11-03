package com.social.join.controllers;

import com.social.join.bootstrapData.DummyData;
import com.social.join.bootstrapData.DummyDataGenerator;
import com.social.join.dtos.CommentDTO;
import com.social.join.dtos.PostDTO;
import com.social.join.entities.Post;
import com.social.join.mappers.ICommentMapper;
import com.social.join.mappers.IPostMapper;
import com.social.join.repositories.ICommentRepository;
import com.social.join.repositories.IPostRepository;
import com.social.join.service.ICommentService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Import({DummyData.class, DummyDataGenerator.class})
class CommentControllerImplTest {

    @MockBean
    private  ICommentController commentController;

    @Autowired
    private ICommentService commentService;

    @Autowired
    private ICommentMapper commentMapper;

    @Autowired
    private ICommentRepository commentRepository;

    @Autowired
    private IPostRepository postRepository;

    @Autowired
    private IPostMapper postMapper;

    private PostDTO dummyPost;

    @BeforeEach
    public void setup() {
        commentController = new CommentControllerImpl();
        Post tempPostInstance = postRepository.findAll().get(1);
        dummyPost = postMapper.postToPostDTO(tempPostInstance);
    }

    @Test
    @Transactional
    void testGetAllComments() {
        // actual posts
        if (commentRepository.findAll().isEmpty()){
            assert false;
        }
        List<CommentDTO> posts = commentRepository.findAll()
                .stream()
                .map(commentMapper::commentToCommentDTO)
                .toList();

        Assertions.assertThat(posts)
                .allMatch(Objects::nonNull);

        // controller's users
        ResponseEntity<List<CommentDTO>> response = commentController.getAllComments(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(posts.size(), Objects.requireNonNull(response.getBody()).size());
        assertEquals(posts.get(0), response.getBody().get(0));
    }

    @Test
    @Transactional
    void testGetCommentById() {
        CommentDTO commentToCheck = commentMapper.commentToCommentDTO(commentRepository.getReferenceById(0));
        assertThat(commentToCheck).isNotNull();

        ResponseEntity<CommentDTO> response = commentController.getCommentById(dummyPost.getId(), commentToCheck.getId());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertThat(Objects.requireNonNull(response.getBody()).getId()).isNotNull();
        assertCommentDTOEquality(commentToCheck, Objects.requireNonNull(response.getBody()));
    }

    @Test
    @Transactional
    void testCreateComment() {
        CommentDTO commentToCreate = commentMapper.commentToCommentDTO(commentRepository.getReferenceById(0));
        assertThat(commentToCreate).isNotNull();
        commentToCreate.setContent("UPDATED_CONTENT");
        commentToCreate.setPost(dummyPost);
        commentToCreate.setUserCreatedIt(dummyPost.getUserCreated());

        ResponseEntity<CommentDTO> response = commentController.createComment(dummyPost.getId(), commentToCreate);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertCommentDTOEquality(commentToCreate, Objects.requireNonNull(response.getBody()));
    }

    @Test
    @Transactional
    void testUpdateComment() {
        CommentDTO commentToUpdate = commentMapper.commentToCommentDTO(commentRepository.getReferenceById(0));
        assertThat(commentToUpdate).isNotNull();
        commentToUpdate.setContent("UPDATED_CONTENT");
        commentToUpdate.setUserCreatedIt(dummyPost.getUserCreated());
        commentToUpdate.setPost(dummyPost);

        ResponseEntity<CommentDTO> response = commentController.updateComment(commentToUpdate.getId(), commentToUpdate.getId(), commentToUpdate);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertCommentDTOEquality(commentToUpdate, Objects.requireNonNull(response.getBody()));
    }

    @Test
    @Transactional
    void testDeleteComment() {
        assert dummyPost.getComments() != null;
        Integer commentIdToDelete = dummyPost.getComments().stream().toList().get(0).getId();
        ResponseEntity<Boolean> response = commentController.deleteComment(
                dummyPost.getId(),
                commentIdToDelete);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertThat(response.getBody()).isTrue();

        assertThat(commentService.deleteCommentById(commentIdToDelete)).isFalse();    // not existing anymore
    }

    private void assertCommentDTOEquality(CommentDTO actualCommentDTO, CommentDTO commentDTO) {
        assertEquals(actualCommentDTO.getId(), commentDTO.getId());
        assertEquals(actualCommentDTO.getPost().getId(), commentDTO.getPost().getId());
        assertEquals(actualCommentDTO.getContent(), commentDTO.getContent());
        assertEquals(actualCommentDTO.getUserCreatedIt(), commentDTO.getUserCreatedIt());
        assertEquals(actualCommentDTO.getCreatedDate(), commentDTO.getCreatedDate());
    }
}