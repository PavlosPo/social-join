package com.social.join.controllers;

import com.social.join.dtos.CommentDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public class CommentControllerImpl implements ICommentController {

    @Override
    public ResponseEntity<List<CommentDTO>> getAllComments(Integer postId) {
        return null;
    }

    @Override
    public ResponseEntity<CommentDTO> getCommentById(Integer postId, Integer commentId) {
        return null;
    }

    @Override
    public ResponseEntity<CommentDTO> createComment(Integer postId, CommentDTO commentDTO) {
        return null;
    }

    @Override
    public ResponseEntity<CommentDTO> updateComment(Integer postId, Integer commentId, CommentDTO commentDTO) {
        return null;
    }

    @Override
    public ResponseEntity<Boolean> deleteComment(Integer postId, Integer commentId) {
        return null;
    }
}
