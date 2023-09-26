package com.social.join.service;

import com.social.join.dtos.CommentDTO;
import com.social.join.dtos.PostDTO;
import com.social.join.dtos.UserDTO;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Primary
public class CommentServiceImpl implements ICommentService {
    @Override
    public Optional<CommentDTO> getCommentById(int id) {
        return Optional.empty();
    }

    @Override
    public CommentDTO createComment(UserDTO userDTO, PostDTO postDTO) {
        return null;
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
