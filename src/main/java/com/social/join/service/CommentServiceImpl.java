package com.social.join.service;

import com.social.join.dtos.CommentCreateRequest;
import com.social.join.dtos.CommentDTO;
import com.social.join.dtos.CommentUpdateRequest;
import com.social.join.mappers.ICommentMapper;
import com.social.join.repositories.ICommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Primary
public class CommentServiceImpl implements ICommentService {
    private final ICommentRepository commentRepository;
    private final ICommentMapper commentMapper;

    @Override
    public Optional<CommentDTO> getCommentById(int id) {
        return Optional.empty();
    }

    @Override
    public CommentDTO createComment(CommentCreateRequest commentCreateRequest) {
        return commentMapper.commentToCommentDTO(commentRepository.save(commentMapper.commentCreateRequestToComment(commentCreateRequest)));
    }
    @Override
    public CommentDTO updateCommentById(CommentUpdateRequest commentUpdateRequest) {
        return commentMapper.commentToCommentDTO(commentRepository.save(commentMapper.commentUpdateRequestToComment(commentUpdateRequest)));
    }

    @Override
    public Boolean deleteCommentById(int id) {
        return null;
    }
}
