package com.social.join.service;

import com.social.join.controllers.NotFoundException;
import com.social.join.dtos.CommentDTO;
import com.social.join.dtos.PostDTO;
import com.social.join.dtos.UserDTO;
import com.social.join.entities.Comment;
import com.social.join.mappers.ICommentMapper;
import com.social.join.repositories.ICommentRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class CommentServiceImplTest {

    @Autowired
    private ICommentService commentService;

    @Autowired
    private ICommentRepository commentRepository;

    @Autowired
    private ICommentMapper commentMapper;

    private UserDTO testUser;
    private PostDTO testPost;
    private CommentDTO testComment;


    @BeforeEach
    void setUp() {
        if (commentRepository.findById(1).isEmpty() ) {
            System.out.println("Not Found the Comment id: " + 1);
            throw new NotFoundException();
        }
        testComment =  commentMapper.commentToCommentDTO(commentRepository.getReferenceById(1));
        testUser = testComment.getUserCreatedIt();
        testPost = testComment.getPost();

    }

    @AfterEach
    void tearDown() {
        testComment = null;
        testUser = null;
        testPost = null;
    }

    @Test
    @Transactional
    void createComment() {
        // Arrange
        testComment.setContent("UPDATED_CONTENT");
        testComment.setCreatedDate(null);
        testComment.setUpdatedDate(null);
//        testComment.setHashtag(null);

        // Act
        CommentDTO returnedComment = commentService.createComment(testUser, testPost, testComment);

        // Assert
        assertCommentEquality(testComment, returnedComment, false);
    }

    @Test
    void getCommentById() {

    }

    @Test
    void updateCommentById() {
    }

    @Test
    void deleteCommentById() {
    }

    /**
     *
     * @param actualComment
     * @param expectedComment
     * @param checkID true if we
     */
    private void assertCommentEquality(CommentDTO actualComment, CommentDTO expectedComment, boolean checkID) {
        assertThat(expectedComment).isNotNull();
        if (checkID){
            assertThat(expectedComment.getId()).isNotNull();
            assertThat(actualComment.getId()).isEqualTo(expectedComment.getId());
        }

        assertThat(expectedComment.getContent()).isEqualTo(actualComment.getContent());
        assertThat(expectedComment.getUserCreatedIt().getId()).isEqualTo(actualComment.getUserCreatedIt().getId());
        assertThat(expectedComment.getPost().getId()).isEqualTo(actualComment.getPost().getId());
        assertThat(expectedComment.getUsersWhoLikedThisComment()).isNotNull();
        assertThat(expectedComment.getCreatedDate()).isNotNull();
        assertThat(expectedComment.getUpdatedDate()).isNotNull();
//        assertThat(expectedComment.getHashtag()).isNotNull();
    }
}