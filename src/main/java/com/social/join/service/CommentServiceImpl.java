package com.social.join.service;

import com.social.join.dtos.CommentDTO;

import java.util.Optional;

public class CommentServiceImpl implements ICommentService {
    @Override
    public Optional<CommentDTO> getCommentById(int id) {
        return Optional.empty();
    }

    @Override
    public CommentDTO updateCommentById(int id, CommentDTO commentDTO) {
        return null;
    }

    @Override
    public Boolean deleteCommentById(int id) {
        return null;
    }
}
