package com.social.join.service;

import com.social.join.dtos.CommentDTO;

import java.util.Optional;

public interface ICommentService {

    /**
     * It returns the {@link CommentDTO} instance of the comment with the corensponding id
     * @param id    the id of the comment
     * @return  {@link Optional} of {@link CommentDTO} instance
     */
    Optional<CommentDTO> getCommentById(int id);

    /**
     * It updates the comment via id
     * @param id    the comment id to update
     * @param commentDTO    the dto instance that will be updated to
     * @return  the updated {@link CommentDTO} instance
     */
    CommentDTO updateCommentById(int id, CommentDTO commentDTO);

    /**
     * It deletes the corresponding comment, searched by id
     * @param id    the comment id to delete
     * @return  true if successfully deleted, false otherwise
     */
    Boolean deleteCommentById(int id);
}
