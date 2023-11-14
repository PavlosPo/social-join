package com.social.join.service;

import com.social.join.dtos.CommentCreateRequest;
import com.social.join.dtos.CommentDTO;
import com.social.join.dtos.CommentUpdateRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface ICommentService {


    /**
     * Here we create a new comment in a post
     *
     * @param commentCreateRequest
     */
    CommentDTO createComment(CommentCreateRequest commentCreateRequest);

    /**
     * It returns the {@link CommentDTO} instance of the comment with the corensponding id
     * @param id    the id of the comment
     * @return  {@link Optional} of {@link CommentDTO} instance
     */
    Optional<CommentDTO> getCommentById(int id);

    /**
     * It updates the comment via id
     *
     * @param commentUpdateRequest@return the updated {@link CommentDTO} instance
     */
    CommentDTO updateCommentById(CommentUpdateRequest commentUpdateRequest);

    /**
     * It deletes the corresponding comment, searched by id
     * @param id    the comment id to delete
     * @return  true if successfully deleted, false otherwise
     */
    Boolean deleteCommentById(int id);
}
