package com.social.join.service;

import com.social.join.dtos.CommentDTO;
import com.social.join.dtos.PostDTO;
import com.social.join.dtos.UserDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface ICommentService {


    /**
     * Here we create a new comment in a post
     *
     * @param userDTO    the user that does the comment
     * @param postDTO    the post that the comment goes to
     * @param commentDTO    the comment instance to create
     * @return the new comment instance as {@link CommentDTO}
     */
    CommentDTO createComment(UserDTO userDTO, PostDTO postDTO, CommentDTO commentDTO);

    /**
     * It returns the {@link CommentDTO} instance of the comment with the corensponding id
     * @param id    the id of the comment
     * @return  {@link Optional} of {@link CommentDTO} instance
     */
    Optional<CommentDTO> getCommentById(int id);

    /**
     * This returns a list of comments made in the given post id
     * @param postId THe post id to search for comments
     * @return  List of CommentsDTO instances
     */
    List<CommentDTO> getCommentsByPostId(int postId);

    /**
     * It updates the comment via id
     *
     * @param id         the comment id to update
     * @param commentDTO the dto instance that will be updated to
     * @return the updated {@link CommentDTO} instance
     */
    Optional<CommentDTO> updateCommentById(int id, CommentDTO commentDTO);

    /**
     * It deletes the corresponding comment, searched by id
     * @param id    the comment id to delete
     * @return  true if successfully deleted, false otherwise
     */
    Boolean deleteCommentById(int id);
}
